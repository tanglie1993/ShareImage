package com.example.demo.dao;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDao extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findById(Integer userId);

    @Query(value="select id from users", nativeQuery=true)
    List<Integer> findAllIds();
}