package com.example.demo.controller;

import com.example.demo.dao.ImagesDao;
import com.example.demo.dao.PostsDao;
import com.example.demo.dao.UsersDao;
import com.example.demo.entity.ImageEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
public class InsertDataController {

    @Autowired
    ImagesDao imagesDao;
    @Autowired
    WebApplicationContext context;

    @RequestMapping(value = "insertData", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file,
                         @RequestParam(value = "user_id", required = true) Integer userId,
                         @RequestParam(value = "timestamp", required = false) Long timestamp) {
        Set<ImageEntity> imageSet = new HashSet<>();
        imageSet.addAll(context.getBean(ImagesDao.class).findAll());
        Set<Integer> userSet = new HashSet<>();
        userSet.addAll(context.getBean(UsersDao.class).findAllIds());
        Set<Integer> postSet = new HashSet<>();
        postSet.addAll(context.getBean(PostsDao.class).findAllImageIds());
        List<UserEntity> newUsers = new ArrayList<>();
        List<PostEntity> newPosts = new ArrayList<>();
        for(ImageEntity image : imageSet){
            if(!userSet.contains(image.getUserId())){
                UserEntity entity = new UserEntity();
                entity.setId(image.getUserId());
                entity.setIntroduction("this is introduction for user " + image.getUserId());
                entity.setNickname("nickname" + image.getUserId());
                entity.setRegisterTimestamp(System.currentTimeMillis());
                entity.setAvatarId(0);
                newUsers.add(entity);
            }
            if(!postSet.contains(image.getId())){
                PostEntity entity = new PostEntity();
                entity.setImageId(image.getId());
                entity.setText("this is comment for pic " + image.getId());
                entity.setTimestamp(System.currentTimeMillis());
                entity.setUserId(image.getUserId());
                newPosts.add(entity);
            }
        }
        context.getBean(UsersDao.class).save(newUsers);
        context.getBean(PostsDao.class).save(newPosts);
        return "";
    }

}
