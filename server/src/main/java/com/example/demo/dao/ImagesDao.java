package com.example.demo.dao;

import com.example.demo.entity.ImagesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesDao extends CrudRepository<ImagesEntity, Long> {
    List<ImagesEntity> findByUserId(Integer userId);
    ImagesEntity findByUserIdAndTimestamp(Integer userId, Long timestamp);
}