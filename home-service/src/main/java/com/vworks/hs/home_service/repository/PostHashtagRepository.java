package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.PostHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  PostHashtagRepository extends JpaRepository<PostHashtagEntity, Long> {
    List<PostHashtagEntity> findByPostId(Long postId);
    void deleteByPostId(Long postId);

}