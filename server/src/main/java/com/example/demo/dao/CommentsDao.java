package com.example.demo.dao;

import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsDao extends CrudRepository<CommentEntity, Long> {

    @Query(value="select * from comments", nativeQuery=true)
    List<CommentEntity> findAll();
}