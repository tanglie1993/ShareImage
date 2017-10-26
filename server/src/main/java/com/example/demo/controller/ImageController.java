package com.example.demo.controller;

import com.example.demo.constants.FileConstants;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static com.example.demo.util.Utils.mkdirs;
import static com.example.demo.util.Utils.getImageEntity;

@RestController
public class ImageController {

    @Autowired
    WebApplicationContext context;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ThumbnailService thumbnailService;

    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file", required = true) MultipartFile file,
                         @RequestParam(value = "user_id", required = true) Integer userId,
                         @RequestParam(value = "timestamp", required = false) Long timestamp) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        if(timestamp == null){
            timestamp = System.currentTimeMillis();
        }
        String fileName = "" + timestamp + ".png";
        String filePath = FileConstants.ROOT_IMAGES + userId + "//";
        File dest = new File(filePath + fileName);
        mkdirs(filePath, dest);
        try {
            thumbnailService.generateThumbnail(file, userId, timestamp);
            file.transferTo(dest);
            context.getBean(ImagesDao.class).save(getImageEntity(userId, timestamp, dest));
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @RequestMapping(value = "comment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadComment(@RequestBody UploadComment comment){
        PostEntity postEntity = context.getBean(PostsDao.class).findById(comment.getPostId());
        if(postEntity == null){
            Map<String, String> result = new HashMap<>();
            result.put("result", "post does not exist");
            return result;
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(comment.getComment());
        commentEntity.setPostId(comment.getPostId());
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setTimestamp(System.currentTimeMillis());
        context.getBean(CommentsDao.class).save(commentEntity);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }

    @RequestMapping(value = "like", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadLike(@RequestBody UploadLike like){
        PostEntity postEntity = context.getBean(PostsDao.class).findById(like.getPostId());
        if(postEntity == null){
            Map<String, String> result = new HashMap<>();
            result.put("result", "post does not exist");
            return result;
        }
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setPostId(like.getPostId());
        likeEntity.setUserId(like.getUserId());
        likeEntity.setTimestamp(System.currentTimeMillis());
        context.getBean(LikesDao.class).save(likeEntity);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }

    @RequestMapping(value = "/imageList", method = RequestMethod.GET)
    public Map<String, Object> getImageList(@RequestParam ("user_id") Integer userId,
                                            @RequestParam (value = "timestamp", required = false) Long timestamp) {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, ImageEntity> imageEntityMap = new HashMap();
        if(timestamp == null){
            timestamp = System.currentTimeMillis();
        }
        for(ImageEntity entity : context.getBean(ImagesDao.class).findByUserId(userId)){
            imageEntityMap.put(entity.getId(), entity);
        }
        Map<Integer, List<CommentEntity>> commentEntityMap = getCommentsMap();
        Map<Integer, List<LikeEntity>> likeEntityMap = getLikesMap();
        Map<Integer, UserEntity> userEntityMap = getUserMap();
        List<PostEntity> postEntityList = new ArrayList<>();
        postEntityList.addAll(context.getBean(PostsDao.class).findLastItems(userId, timestamp, 3));
        UserEntity userEntity = context.getBean(UsersDao.class).findById(userId);
        List<PostView> postViewList = new ArrayList<>();
        for(PostEntity postEntity : postEntityList){
            PostView postView = new PostView();
            postView.setText(postEntity.getText());
            postView.setTimeStamp(postEntity.getTimestamp());
            postView.setUserAvatar("" + userEntity.getAvatarId());
            postView.setUserName(userEntity.getNickname());
            postView.setUserSex(userEntity.getGender());
            postView.setId(postEntity.getId());
            ImageEntity image = imageEntityMap.get(postEntity.getImageId());
            if(image != null){
                postView.setImageUrl("" + image.getTimestamp());
                postView.setImageWidth(image.getWidth());
                postView.setImageHeight(image.getHeight());
            }
            if(commentEntityMap.containsKey(postEntity.getId())){
                for(CommentEntity commentEntity : commentEntityMap.get(postEntity.getId())){
                    postView.getComments().add(commentEntity.getContent());
                }
            }
            if(likeEntityMap.containsKey(postEntity.getId())){
                for(LikeEntity likeEntity : likeEntityMap.get(postEntity.getId())){
                    postView.getLikes().add(userEntityMap.get(likeEntity.getUserId()).getNickname());
                }
            }
            postViewList.add(postView);
        }
        result.put("list", postViewList);
        return result;
    }

    private Map<Integer, UserEntity> getUserMap() {
        Map<Integer, UserEntity> userEntityMap = new HashMap<>();
        for(UserEntity user : context.getBean(UsersDao.class).findAll()){
            userEntityMap.put(user.getId(), user);
        }
        return userEntityMap;
    }

    private Map<Integer, List<LikeEntity>> getLikesMap() {
        Map<Integer, List<LikeEntity>> likeEntityMap = new HashMap<>();
        for(LikeEntity entity : context.getBean(LikesDao.class).findAll()){
            List<LikeEntity> list;
            if(likeEntityMap.containsKey(entity.getPostId())){
                list = likeEntityMap.get(entity.getPostId());
            }else {
                list = new ArrayList<>();
            }
            list.add(entity);
            likeEntityMap.put(entity.getPostId(), list);
        }
        return likeEntityMap;
    }

    private Map<Integer, List<CommentEntity>> getCommentsMap() {
        Map<Integer, List<CommentEntity>> commentEntityMap = new HashMap<>();
        for(CommentEntity entity : context.getBean(CommentsDao.class).findAll()){
            List<CommentEntity> list;
            if(commentEntityMap.containsKey(entity.getPostId())){
               list = commentEntityMap.get(entity.getPostId());
            }else {
                list = new ArrayList<>();
            }
            list.add(entity);
            commentEntityMap.put(entity.getPostId(), list);
        }
        return commentEntityMap;
    }

    @RequestMapping(value = "/image/{user_id}/{name}.{format}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable ("user_id") Integer userId,
                                     @PathVariable ("name") String name,
                                     @PathVariable ("format") String format) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" +
                    Paths.get(FileConstants.ROOT_IMAGES + userId , "" + name + "." + format).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Map<String, Object> hello(@RequestParam ("user_id") Integer userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("hello", "hello");
        return result;
    }

}
