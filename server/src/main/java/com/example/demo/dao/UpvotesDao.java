package com.example.demo.dao;

import com.example.demo.entity.UpvoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpvotesDao extends CrudRepository<UpvoteEntity, Long> {
    List<UpvoteEntity> findByUserId(Integer userId);
    List<UpvoteEntity> findAll();
    UpvoteEntity findByUserIdAndTimestamp(Integer userId, Long timestamp);
}