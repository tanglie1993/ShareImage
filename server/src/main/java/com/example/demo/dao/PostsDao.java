package com.example.demo.dao;

import com.example.demo.entity.ImageEntity;
import com.example.demo.entity.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostsDao extends CrudRepository<PostEntity, Long> {

    @Query(value="SELECT * FROM posts WHERE user_id = ?1 AND timestamp <= ?2 ORDER BY TIMESTAMP DESC LIMIT ?3", nativeQuery=true)
    List<PostEntity> findLastItems(Integer userId, Long timestamp, Integer limit);
    PostEntity findById(Integer id);
    List<PostEntity> findAll();

    @Query(value="select image_id from posts", nativeQuery=true)
    List<Integer> findAllImageIds();

    PostEntity findByImageId(int id);
}