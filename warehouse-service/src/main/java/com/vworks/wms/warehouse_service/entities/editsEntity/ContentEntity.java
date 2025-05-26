package com.vworks.wms.warehouse_service.entities.editsEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contents", schema = "warehouse-service", catalog = "db-cuongphong-warehouse-mngt")
public class ContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls", columnDefinition = "TEXT")
    private List<String> imageUrls = new ArrayList<>();


    @Column(nullable = false)
    private Integer position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
