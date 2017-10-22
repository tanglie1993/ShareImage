package com.example.demo.controller;

import com.example.demo.constants.FileConstants;
import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.ImagesDao;
import com.example.demo.dao.PostsDao;
import com.example.demo.dao.UsersDao;
import com.example.demo.entity.*;
import com.example.demo.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class ImageController {

    @Autowired
    ImagesDao imagesDao;

    @Autowired
    PostsDao postsDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    CommentsDao commentsDao;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ThumbnailService thumbnailService;

    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file,
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
            saveToDatabase(userId, timestamp, dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    private void saveToDatabase(@RequestParam(value = "user_id", required = true) Integer userId, Long timestamp, File dest) throws IOException {
        ImageEntity entity = new ImageEntity();
        entity.setUserId(userId);
        entity.setTimestamp(timestamp);
        String[] splitedName = dest.getName().split("\\.");
        if(splitedName.length > 0){
            entity.setFormat(splitedName[splitedName.length - 1]);
        }else{
            entity.setFormat("unknown");
        }
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
        entity.setWidth(sourceImg.getWidth());
        entity.setHeight(sourceImg.getHeight());
        entity.setBytes(dest.length());
        imagesDao.save(entity);
    }

    private void mkdirs(String filePath, File dest) {
        if(!new File(filePath).exists()){
            new File(filePath).mkdirs();
        }
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
    }


    @RequestMapping(value = "/imageList", method = RequestMethod.GET)
    public Map<String, Object> getImageList(@RequestParam ("user_id") Integer userId) {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, ImageEntity> imageEntityMap = new HashMap();
        for(ImageEntity entity : imagesDao.findByUserId(userId)){
            imageEntityMap.put(entity.getId(), entity);
        }
        Map<Integer, List<CommentEntity>> commentEntityMap = new HashMap<>();
        for(CommentEntity entity : commentsDao.findAll()){
            List<CommentEntity> list;
            if(commentEntityMap.containsKey(entity.getPostId())){
               list = commentEntityMap.get(entity.getPostId());
            }else {
                list = new ArrayList<>();
            }
            list.add(entity);
            commentEntityMap.put(entity.getPostId(), list);
        }
        List<PostEntity> postEntityList = new ArrayList<>();
        postEntityList.addAll(postsDao.findByUserId(userId));
        UserEntity userEntity = usersDao.findById(userId);
        List<PostView> postViewList = new ArrayList<>();
        for(PostEntity postEntity : postEntityList){
            PostView postView = new PostView();
            postView.setText(postEntity.getText());
            postView.setTimeStamp(postEntity.getTimestamp());
            postView.setUserAvatar("" + userEntity.getAvatarId());
            postView.setUserName(userEntity.getNickname());
            postView.setUserSex(userEntity.getGender());
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
            postViewList.add(postView);
        }
        result.put("list", postViewList);
        return result;
    }

    @RequestMapping(value = "/image/{user_id}/{name}.{format}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable ("user_id") Integer userId,
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
