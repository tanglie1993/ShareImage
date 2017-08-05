package com.example.demo.dao;

import com.example.demo.entity.ImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesDao extends CrudRepository<ImageEntity, Long> {
    List<ImageEntity> findByUserId(Integer userId);
    List<ImageEntity> findAll();
    ImageEntity findByUserIdAndTimestamp(Integer userId, Long timestamp);
}