package com.vworks.wms.warehouse_service.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vworks.wms.common_lib.config.MinioConfigProperties;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.service.MinioService;
import com.vworks.wms.common_lib.utils.StatusUtil;
import com.vworks.wms.warehouse_service.entities.DetailMaterialsEntity;
import com.vworks.wms.warehouse_service.entities.MaterialsEntity;
import com.vworks.wms.warehouse_service.entities.UnitTypeEntity;
import com.vworks.wms.warehouse_service.models.request.DetailWholesalePrice;
import com.vworks.wms.warehouse_service.models.request.material.*;
import com.vworks.wms.warehouse_service.models.response.material.*;
import com.vworks.wms.warehouse_service.repository.DetailMaterialsRepository;
import com.vworks.wms.warehouse_service.repository.MaterialsRepository;
import com.vworks.wms.warehouse_service.repository.UnitTypeRepository;
import com.vworks.wms.warehouse_service.service.MaterialService;
import com.vworks.wms.warehouse_service.utils.Commons;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final DetailMaterialsRepository detailMaterialsRepository;
    private final MaterialsRepository materialsRepository;
    private final UnitTypeRepository unitTypeRepository;
    private final ModelMapper modelMapper;
    private final MinioService minioService;
    private final MinioConfigProperties minioConfigProperties;
    private final Gson gson;

    @Override
    public Page<PostListMaterialResponse> postListMaterial(PostListMaterialRequest requestBody) {
        log.info("{} postListMaterial requestBody {}", getClass().getSimpleName(), requestBody);
        Pageable pageable = PageRequest.of(requestBody.getPage() - 1, requestBody.getLimit(), Sort.by("createdDate").descending());

        Page<DetailMaterialsEntity> page = detailMaterialsRepository.findAll(detailMaterialsSpec(requestBody), pageable);

        List<PostListMaterialResponse> list = page.getContent().stream().map(e ->
                modelMapper.map(e, PostListMaterialResponse.class)
        ).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public PostCreateMaterialResponse postCreateMaterial(PostCreateMaterialRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postCreateMaterial requestBody {}", getClass().getSimpleName(), requestBody);

        Optional<DetailMaterialsEntity> optionalDetailMaterialsCode = detailMaterialsRepository.findByCodeOrName(requestBody.getCode(), null);

        if (optionalDetailMaterialsCode.isPresent()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.CODE_EXIST.getCode(), ExceptionTemplate.CODE_EXIST.getMessage());
        }
        Optional<DetailMaterialsEntity> optionalDetailMaterialsName = detailMaterialsRepository.findByCodeOrName(null, requestBody.getName());

        if (optionalDetailMaterialsName.isPresent()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.NAME_EXIST.getCode(), ExceptionTemplate.NAME_EXIST.getMessage());
        }

        Optional<MaterialsEntity> optionalMaterials = materialsRepository.findByCodeOrName(requestBody.getMaterialTypeCode(), null);
        if (optionalMaterials.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }

        Optional<UnitTypeEntity> optionalUnitType = unitTypeRepository.findByCodeOrName(requestBody.getUnitTypeCode(), null);
        if (optionalUnitType.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }

        String pathFile = minioConfigProperties.getMaterialImageFolderStorage() + requestBody.getCode() + "_" + requestBody.getName();
        String pathFileImage = minioService.uploadFileToMinio(requestBody.getImage(), minioConfigProperties.getBucketName(), pathFile);


        DetailMaterialsEntity detailMaterialsEntity = new DetailMaterialsEntity();
        detailMaterialsEntity.setId(UUID.randomUUID().toString());
        detailMaterialsEntity.setCode(requestBody.getCode());
        detailMaterialsEntity.setName(requestBody.getName());
        detailMaterialsEntity.setMaterialTypeCode(requestBody.getMaterialTypeCode());
        detailMaterialsEntity.setMeasureKeyword(requestBody.getUnitTypeCode());
        detailMaterialsEntity.setListPrice(requestBody.getListPrice());
//        detailMaterialsEntity.setParameter(requestBody.getParameter());
        detailMaterialsEntity.setOrigin(requestBody.getOrigin());
        detailMaterialsEntity.setMinInventory(requestBody.getMinInventory());
        detailMaterialsEntity.setDiscount(gson.toJson(requestBody.getDetailWholesalePrice()));
        if (StringUtils.isNotEmpty(pathFileImage)) {
            detailMaterialsEntity.setImage(pathFileImage);
        }
        detailMaterialsEntity.setStatus(requestBody.getStatus());
        detailMaterialsEntity.setDescription(requestBody.getDescription());
        detailMaterialsEntity.setCreatedBy(StringUtils.isBlank(httpServletRequest.getHeader(Commons.USER_CODE_FIELD)) ? httpServletRequest.getHeader(Commons.USER_CODE_FIELD) : null);
        detailMaterialsEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        detailMaterialsRepository.save(detailMaterialsEntity);
        return modelMapper.map(detailMaterialsEntity, PostCreateMaterialResponse.class);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public PostUpdateMaterialResponse postUpdateMaterial(PostUpdateMaterialRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postUpdateMaterial requestBody {}", getClass().getSimpleName(), requestBody);
        Optional<DetailMaterialsEntity> optionalDetailMaterials = detailMaterialsRepository.findById(requestBody.getId());
        if (optionalDetailMaterials.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }
        Optional<DetailMaterialsEntity> optionalDetailMaterialsCode = detailMaterialsRepository.findByCodeOrName(requestBody.getCode(), null);
        if (optionalDetailMaterialsCode.isPresent() && !StringUtils.equals(optionalDetailMaterialsCode.get().getCode(), optionalDetailMaterials.get().getCode())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.CODE_EXIST.getCode(), ExceptionTemplate.CODE_EXIST.getMessage());
        }
        Optional<DetailMaterialsEntity> optionalDetailMaterialsName = detailMaterialsRepository.findByCodeOrName(null, requestBody.getName());

        if (optionalDetailMaterialsName.isPresent() && !StringUtils.equals(optionalDetailMaterialsName.get().getName(), optionalDetailMaterials.get().getName())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.NAME_EXIST.getCode(), ExceptionTemplate.NAME_EXIST.getMessage());
        }

        Optional<MaterialsEntity> optionalMaterials = materialsRepository.findByCodeOrName(requestBody.getMaterialTypeCode(), null);
        if (optionalMaterials.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }

        Optional<UnitTypeEntity> optionalUnitType = unitTypeRepository.findByCodeOrName(requestBody.getUnitTypeCode(), null);
        if (optionalUnitType.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }

        String pathFile = minioConfigProperties.getMaterialImageFolderStorage() + requestBody.getCode() + "_" + requestBody.getName();
        String pathFileImage = minioService.uploadFileToMinio(requestBody.getImage(), minioConfigProperties.getBucketName(), pathFile);

        DetailMaterialsEntity detailMaterialsEntity = optionalDetailMaterials.get();
        detailMaterialsEntity.setCode(requestBody.getCode());
        detailMaterialsEntity.setName(requestBody.getName());
        detailMaterialsEntity.setMaterialTypeCode(requestBody.getMaterialTypeCode());
        detailMaterialsEntity.setMeasureKeyword(requestBody.getUnitTypeCode());
        detailMaterialsEntity.setListPrice(requestBody.getListPrice());
//        detailMaterialsEntity.setParameter(requestBody.getParameter());
        detailMaterialsEntity.setOrigin(requestBody.getOrigin());
        detailMaterialsEntity.setMinInventory(requestBody.getMinInventory());
        detailMaterialsEntity.setDiscount(gson.toJson(requestBody.getDetailWholesalePrice()));
        if (StringUtils.isNotEmpty(pathFileImage)) {
            detailMaterialsEntity.setImage(pathFileImage);
        }
        detailMaterialsEntity.setStatus(requestBody.getStatus());
        detailMaterialsEntity.setDescription(requestBody.getDescription());
        detailMaterialsEntity.setUpdatedBy(StringUtils.isBlank(httpServletRequest.getHeader(Commons.USER_CODE_FIELD)) ? httpServletRequest.getHeader(Commons.USER_CODE_FIELD) : null);
        detailMaterialsEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        detailMaterialsRepository.save(detailMaterialsEntity);

        return modelMapper.map(detailMaterialsEntity, PostUpdateMaterialResponse.class);
    }

    @Override
    public PostDetailMaterialResponse postDetailMaterial(PostDetailMaterialRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postDetailMaterial requestBody {}", getClass().getSimpleName(), requestBody);
        Optional<DetailMaterialsEntity> optionalDetailMaterials = detailMaterialsRepository.findById(requestBody.getId());
        if (optionalDetailMaterials.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }
        DetailMaterialsEntity detailMaterialsEntity = optionalDetailMaterials.get();
        return PostDetailMaterialResponse.builder()
                .id(detailMaterialsEntity.getId())
                .code(detailMaterialsEntity.getCode())
                .name(detailMaterialsEntity.getName())
                .materialTypeCode(detailMaterialsEntity.getMaterialTypeCode())
                .unitTypeCode(detailMaterialsEntity.getMeasureKeyword())
                .listPrice(detailMaterialsEntity.getListPrice())
//                .parameter(detailMaterialsEntity.getParameter())
                .minInventory(detailMaterialsEntity.getMinInventory())
                .origin(detailMaterialsEntity.getOrigin())
                .detailWholesalePrice(gson.fromJson(detailMaterialsEntity.getDiscount(), new TypeToken<List<DetailWholesalePrice>>() {
                }.getType()))
                .image(detailMaterialsEntity.getImage())
                .description(detailMaterialsEntity.getDescription())
                .status(detailMaterialsEntity.getStatus())
                .createdBy(detailMaterialsEntity.getCreatedBy())
                .createdDate(detailMaterialsEntity.getCreatedDate())
                .updatedBy(detailMaterialsEntity.getUpdatedBy())
                .updatedDate(detailMaterialsEntity.getUpdatedDate())
                .build();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public PostDeleteMaterialResponse postDeleteMaterial(PostDeleteMaterialRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postDeleteMaterial requestBody {}", getClass().getSimpleName(), requestBody);
        Optional<DetailMaterialsEntity> optionalDetailMaterials = detailMaterialsRepository.findById(requestBody.getId());
        if (optionalDetailMaterials.isEmpty()) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }
        if (StringUtils.equals(optionalDetailMaterials.get().getStatus(), StatusUtil.ACTIVE.name())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.STATUS_INVALID.getCode(), ExceptionTemplate.STATUS_INVALID.getMessage());
        }
        DetailMaterialsEntity detailMaterialsEntity = optionalDetailMaterials.get();
        detailMaterialsEntity.setStatus(StatusUtil.DELETED.name());
        detailMaterialsRepository.save(detailMaterialsEntity);
        return modelMapper.map(detailMaterialsEntity, PostDeleteMaterialResponse.class);
    }

    @Override
    public List<PostGetByConditionResponseBody> postGetByCondition(PostGetByConditionRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postGetByCondition requestBody {}", getClass().getSimpleName(), requestBody);
        List<DetailMaterialsEntity> detailMaterialsEntities = detailMaterialsRepository.findAllByMaterialTypeCode(requestBody.getMaterialTypeCode());
        List<MaterialsEntity> materialsEntities = materialsRepository.findAll();
        List<UnitTypeEntity> unitTypeEntities = unitTypeRepository.findAll();
        return detailMaterialsEntities.stream().map(e -> {
                    List<DetailWholesalePrice> detailWholesalePrices = gson.fromJson(e.getDiscount(), new TypeToken<List<DetailWholesalePrice>>() {
                    }.getType());
                    MaterialsEntity materialsEntity = materialsEntities.stream().filter(f -> StringUtils.equals(f.getCode(), e.getMaterialTypeCode())).findFirst().orElse(null);
                    UnitTypeEntity unitTypeEntity = unitTypeEntities.stream().filter(f -> StringUtils.equals(f.getCode(), e.getMeasureKeyword())).findFirst().orElse(null);
                    return PostGetByConditionResponseBody.builder()
                            .id(e.getId())
                            .code(e.getCode())
                            .name(e.getName())
                            .materialTypeCode(materialsEntity.getCode())
                            .materialTypeName(materialsEntity.getName())
                            .unitTypeCode(unitTypeEntity.getCode())
                            .unitTypeName(unitTypeEntity.getName())
//                            .parameter(e.getParameter())
                            .price(StringUtils.isBlank(requestBody.getPositionCode()) ? e.getListPrice() : getPrice(detailWholesalePrices, e.getListPrice(), requestBody.getPositionCode()))
                            .build();
                }

        ).toList();
    }

    @Override
    public Object postDetailMaterialList(PostDetailMaterialListRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postDetailMaterialList requestBody {}", getClass().getSimpleName(), requestBody);
        List<DetailMaterialsEntity> detailMaterialsEntityList = detailMaterialsRepository.findAllByCodeIn(requestBody.getCodeList());
        if (CollectionUtils.isEmpty(detailMaterialsEntityList)) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.DATA_NOT_FOUND.getCode(), ExceptionTemplate.DATA_NOT_FOUND.getMessage());
        }

        return detailMaterialsEntityList.stream().map(detailMaterialsEntity -> PostDetailMaterialResponse.builder()
                        .id(detailMaterialsEntity.getId())
                        .code(detailMaterialsEntity.getCode())
                        .name(detailMaterialsEntity.getName())
                        .materialTypeCode(detailMaterialsEntity.getMaterialTypeCode())
                        .unitTypeCode(detailMaterialsEntity.getMeasureKeyword())
                        .listPrice(detailMaterialsEntity.getListPrice())
//                        .parameter(detailMaterialsEntity.getParameter())
                        .minInventory(detailMaterialsEntity.getMinInventory())
                        .origin(detailMaterialsEntity.getOrigin())
                        .detailWholesalePrice(gson.fromJson(detailMaterialsEntity.getDiscount(), new TypeToken<List<DetailWholesalePrice>>() {
                        }.getType()))
                        .image(detailMaterialsEntity.getImage())
                        .description(detailMaterialsEntity.getDescription())
                        .status(detailMaterialsEntity.getStatus())
                        .createdBy(detailMaterialsEntity.getCreatedBy())
                        .createdDate(detailMaterialsEntity.getCreatedDate())
                        .updatedBy(detailMaterialsEntity.getUpdatedBy())
                        .updatedDate(detailMaterialsEntity.getUpdatedDate())
                        .build())
                .toList();
    }

    private BigDecimal getPrice(List<DetailWholesalePrice> detailWholesalePrices, BigDecimal listPrice, String positionCode) {
        if (CollectionUtils.isEmpty(detailWholesalePrices)) {
            return listPrice;
        } else {
            List<DetailWholesalePrice> detailWholesaleFilterPosition = detailWholesalePrices.stream().filter(e -> StringUtils.equals(e.getPositionCode(), positionCode)).toList();
            if (CollectionUtils.isEmpty(detailWholesaleFilterPosition)) {
                return listPrice;
            } else {
                return detailWholesaleFilterPosition.get(0).getValue();
            }
        }
    }

    private Specification<DetailMaterialsEntity> detailMaterialsSpec(PostListMaterialRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.notEqual(root.get("status"), StatusUtil.DELETED.name()));
            if (StringUtils.isNotBlank(request.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }
            String valueSearchText = "%" + request.getSearchText() + "%";
            if (StringUtils.isNotEmpty(request.getSearchText())) {
                Predicate code = criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), valueSearchText.toLowerCase());
                Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), valueSearchText.toLowerCase());
                predicates.add(criteriaBuilder.or(code, name));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
