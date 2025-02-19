package com.vworks.wms.warehouse_service.service.impl;

import com.google.gson.Gson;
import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.service.ServiceUtils;
import com.vworks.wms.common_lib.utils.Commons;
import com.vworks.wms.common_lib.utils.StatusUtil;
import com.vworks.wms.warehouse_service.entities.DetailMaterialsEntity;
import com.vworks.wms.warehouse_service.entities.DetailOrderEntity;
import com.vworks.wms.warehouse_service.entities.MaterialsEntity;
import com.vworks.wms.warehouse_service.entities.OrderEntity;
import com.vworks.wms.warehouse_service.models.MaterialOrderModel;
import com.vworks.wms.warehouse_service.models.request.order.*;
import com.vworks.wms.warehouse_service.models.response.order.PostDetailOrderResBody;
import com.vworks.wms.warehouse_service.repository.*;
import com.vworks.wms.warehouse_service.service.OrderService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ObjectRepository objectRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final DetailMaterialsRepository detailMaterialsRepository;
    private final MaterialsRepository materialsRepository;
    private final DetailOrderRepository detailOrderRepository;
    private final ServiceUtils serviceUtils;

    @Override
    public Page<OrderEntity> postListOrder(PostListOrderReqBody reqBody) {
        log.info("{} postListOrder reqBody {}", getClass().getSimpleName(), reqBody);
        Pageable pageable = PageRequest.of(reqBody.getPage() - 1, reqBody.getLimit(), Sort.by("createdDate").descending());
        return orderRepository.findAll(orderSpec(reqBody), pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object postCreateOrder(PostCreateOrderReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postCreateOrder reqBody {}", getClass().getSimpleName(), reqBody);

        checkValid(reqBody);

        long countOrder = orderRepository.count();

        List<DetailMaterialsEntity> detailMaterialsEntities = checkDetailMaterial(reqBody);

        List<MaterialsEntity> materialsEntities = materialsRepository.findAll();
        OrderEntity orderEntity = new OrderEntity();
        buildCreateOrder(orderEntity, reqBody);
        orderEntity.setId(UUID.randomUUID().toString());
        orderEntity.setCode("ORDER" + (countOrder + 1));
        orderEntity.setStatus(StatusUtil.CREATED.name());
        orderEntity.setCreatedBy(serviceUtils.getUserHeader(httpServletRequest));
        orderEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(orderEntity);

        detailOrderRepository.saveAll(buildDetailOrderEntities(reqBody, detailMaterialsEntities, materialsEntities, orderEntity));
        return StatusUtil.SUCCESS;
    }

    @Override
    public Object postUpdateOrder(PostUpdateOrderReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postListOrder reqBody {}", getClass().getSimpleName(), reqBody);
        OrderEntity orderEntity = orderRepository.findByCode(reqBody.getCode()).orElseThrow(() -> new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage()));

        checkValid(reqBody);

        List<DetailMaterialsEntity> detailMaterialsEntities = checkDetailMaterial(reqBody);

        List<MaterialsEntity> materialsEntities = materialsRepository.findAll();

        buildCreateOrder(orderEntity, reqBody);
        orderEntity.setStatus(StatusUtil.CREATED.name());
        orderEntity.setUpdatedBy(serviceUtils.getUserHeader(httpServletRequest));
        orderEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(orderEntity);

        List<DetailOrderEntity> detailOrderEntities = detailOrderRepository.findAllByOrderCode(orderEntity.getCode());
        detailOrderRepository.deleteAll(detailOrderEntities);

        detailOrderRepository.saveAll(buildDetailOrderEntities(reqBody, detailMaterialsEntities, materialsEntities, orderEntity));
        return StatusUtil.SUCCESS;
    }

    @Override
    public PostDetailOrderResBody postDetailOrder(PostDetailOrderReqBody reqBody) throws WarehouseMngtSystemException {
        log.info("{} postListOrder reqBody {}", getClass().getSimpleName(), reqBody);
        OrderEntity orderEntity = orderRepository.findByCode(reqBody.getCode()).orElseThrow(() -> new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage()));

        List<DetailOrderEntity> detailOrderEntities = detailOrderRepository.findAllByOrderCode(orderEntity.getCode());

        List<MaterialOrderModel> materialOrderModels = detailOrderEntities.stream().map(e ->
                MaterialOrderModel.builder()
                        .code(e.getMaterialCode())
                        .name(e.getMaterialName())
                        .price(e.getPrice())
                        .quantity(e.getQuantity())
                        .build()
        ).toList();
        return PostDetailOrderResBody.builder()
                .order(orderEntity)
                .materialOrders(materialOrderModels)
                .build();
    }

    private List<DetailOrderEntity> buildDetailOrderEntities(BaseOrderReqBody reqBody,
                                                             List<DetailMaterialsEntity> detailMaterialsEntities,
                                                             List<MaterialsEntity> materialsEntities,
                                                             OrderEntity orderEntity) {
        List<DetailOrderEntity> a = detailMaterialsEntities.stream().map(e ->
                DetailOrderEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .orderCode(orderEntity.getCode())
                        .materialCode(e.getCode())
                        .materialName(e.getName())
                        .materialTypeName(Objects.requireNonNull(materialsEntities.stream()
                                .filter(f -> f.getCode().equals(e.getMaterialTypeCode()))
                                .findFirst().orElse(null)).getName())
                        .materialTypeCode(e.getMaterialTypeCode())
                        .price(Objects.requireNonNull(reqBody.getMaterialOrders().stream()
                                .filter(f -> f.getCode().equals(e.getCode()))
                                .findFirst().orElse(null)).getPrice())
                        .quantity(Objects.requireNonNull(reqBody.getMaterialOrders().stream()
                                .filter(f -> f.getCode().equals(e.getCode()))
                                .findFirst().orElse(null)).getQuantity())
                        .status(StatusUtil.ACTIVE.name())
                        .build()
        ).collect(Collectors.toList());
        log.info("{} buildDetailOrderEntities countDetailOrderEntity{}", getClass().getSimpleName(), a.size());
        return a;
    }

    private void buildCreateOrder(OrderEntity orderEntity, BaseOrderReqBody reqBody) {
        orderEntity.setOrderType(reqBody.getOrderType());
        orderEntity.setCustomerCode(reqBody.getCustomerCode());
        orderEntity.setCustomerType(reqBody.getCustomerType());
        orderEntity.setDeliveryMethod(reqBody.getDeliveryMethod());
        orderEntity.setExchangeRateCode(reqBody.getExchangeRateCode());
        orderEntity.setTotal(calculateTotalOrder(reqBody.getMaterialOrders()));
    }


    private BigDecimal calculateTotalOrder(List<MaterialOrderModel> materialOrders) {
        return materialOrders.stream()
                .map(e -> e.getPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void checkValid(BaseOrderReqBody reqBody) throws WarehouseMngtSystemException {
        objectRepository.findByCodeOrName(reqBody.getCustomerCode(), null)
                .orElseThrow(() -> new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage()));

        exchangeRateRepository.findByCodeOrName(reqBody.getExchangeRateCode(), null)
                .orElseThrow(() -> new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage()));

        if (CollectionUtils.isEmpty(reqBody.getMaterialOrders())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.REQUEST_INVALID.getCode(), ExceptionTemplate.REQUEST_INVALID.getMessage());
        }
        for (MaterialOrderModel x : reqBody.getMaterialOrders()) {
            if (x.getQuantity() <= 0) {
                throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.REQUEST_INVALID.getCode(), ExceptionTemplate.REQUEST_INVALID.getMessage());
            }
        }
    }

    private List<DetailMaterialsEntity> checkDetailMaterial(BaseOrderReqBody reqBody) throws WarehouseMngtSystemException {
        List<String> codeMaterials = reqBody.getMaterialOrders().stream().map(MaterialOrderModel::getCode).collect(Collectors.toList());

        List<DetailMaterialsEntity> detailMaterialsEntities = detailMaterialsRepository.findAllByCodeIn(codeMaterials);

        if (codeMaterials.size() != detailMaterialsEntities.size()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }
        return detailMaterialsEntities;
    }

    private Specification<OrderEntity> orderSpec(PostListOrderReqBody request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.notEqual(root.get("status"), StatusUtil.DELETED.name()));
            if (StringUtils.isNotBlank(request.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }
            String valueSearchText = "%" + request.getSearchText() + "%";
            if (StringUtils.isNotEmpty(request.getSearchText())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), valueSearchText.toLowerCase()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public BaseResponse<?> postUpdateStatusOrder(PostUpdateStatusOrderRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("[START] {} postUpdateStatusOrder with requestBody = {}", this.getClass().getSimpleName(), new Gson().toJson(requestBody));
        OrderEntity orderEntity = orderRepository.findByCode(requestBody.getCode()).orElseThrow(() -> new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage()));
        orderEntity.setStatus(requestBody.getStatus());
        orderEntity.setUpdatedBy(httpServletRequest.getHeader(Commons.FIELD_USER_CODE));
        orderEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(orderEntity);
        return new BaseResponse<>();
    }
}
