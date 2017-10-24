package com.example.demo.dao;

import com.example.demo.entity.LikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesDao extends CrudRepository<LikeEntity, Long> {
    LikeEntity findByPostId(Integer postId);
}