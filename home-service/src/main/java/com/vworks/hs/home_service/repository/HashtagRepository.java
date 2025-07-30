package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
}