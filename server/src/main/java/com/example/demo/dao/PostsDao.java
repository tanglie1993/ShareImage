package com.example.demo.dao;

import com.example.demo.entity.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostsDao extends CrudRepository<PostEntity, Long> {
    List<PostEntity> findByUserId(Integer userId);
    List<PostEntity> findAll();
    PostEntity findByUserIdAndTimestamp(Integer userId, Long timestamp);

    @Query(value="select image_id from posts", nativeQuery=true)
    List<Integer> findAllImageIds();
}