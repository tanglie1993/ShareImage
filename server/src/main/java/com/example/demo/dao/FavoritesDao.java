package com.example.demo.dao;

import com.example.demo.entity.FavoriteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesDao extends CrudRepository<FavoriteEntity, Long> {
    List<FavoriteEntity> findByUserId(Integer userId);
    List<FavoriteEntity> findAll();
    FavoriteEntity findByUserIdAndTimestamp(Integer userId, Long timestamp);
}