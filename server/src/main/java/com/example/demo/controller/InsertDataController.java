package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.ThumbnailService;
import com.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.*;

import static com.example.demo.constants.FileConstants.ROOT_IMAGES;
import static com.example.demo.util.Utils.mkdirs;

@RestController
public class InsertDataController {

    @Autowired
    WebApplicationContext context;

    @Autowired
    ThumbnailService thumbnailService;

    int[] users = {42, 43};

    @RequestMapping(value = "insertMissingUsers", method = RequestMethod.POST)
    @ResponseBody
    public String insertMissingUsers() {
        Set<ImageEntity> imageSet = new HashSet<>();
        imageSet.addAll(context.getBean(ImagesDao.class).findAll());
        Set<Integer> userSet = new HashSet<>();
        userSet.addAll(context.getBean(UsersDao.class).findAllIds());
        List<UserEntity> newUsers = new ArrayList<>();
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
        }
        context.getBean(UsersDao.class).save(newUsers);
        return "";
    }

    @RequestMapping(value = "insertMissingPosts", method = RequestMethod.POST)
    @ResponseBody
    public String insertMissingPosts() {
        Set<ImageEntity> imageSet = new HashSet<>();
        imageSet.addAll(context.getBean(ImagesDao.class).findAll());
        Set<Integer> postSet = new HashSet<>();
        postSet.addAll(context.getBean(PostsDao.class).findAllImageIds());
        List<PostEntity> newPosts = new ArrayList<>();
        for(ImageEntity image : imageSet){
            if(!postSet.contains(image.getId())){
                PostEntity entity = new PostEntity();
                entity.setImageId(image.getId());
                entity.setText("this is comment for pic " + image.getId());
                entity.setTimestamp(System.currentTimeMillis());
                entity.setUserId(image.getUserId());
                newPosts.add(entity);
            }
        }
        context.getBean(PostsDao.class).save(newPosts);
        return "";
    }

    @RequestMapping(value = "generateRandomData", method = RequestMethod.POST)
    @ResponseBody
    public String generateRandomData() {
        List<ImageEntity> imageList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            imageList.add(uploadRandomPic(i));
        }
        context.getBean(ImagesDao.class).save(imageList);
        context.getBean(PostsDao.class).save(generatePostList(imageList));
        context.getBean(CommentsDao.class).save(generateRandomComments(imageList));
        context.getBean(LikesDao.class).save(generateRandomLikes(imageList));
        return "";
    }

    private List<LikeEntity> generateRandomLikes(List<ImageEntity> imageList) {
        List<LikeEntity> result = new ArrayList<>();
        Map<Integer, PostEntity> posts = new HashMap<>();
        for(ImageEntity imageEntity : imageList){
            PostEntity postEntity = context.getBean(PostsDao.class).findByImageId(imageEntity.getId());
            if(postEntity != null){
                posts.put(postEntity.getImageId(), postEntity);
            }
        }
        for(ImageEntity image : imageList){
            if(new Random().nextInt(2) == 0){
                LikeEntity likeEntity = new LikeEntity();
                likeEntity.setPostId(posts.get(image.getId()).getId());
                likeEntity.setTimestamp(System.currentTimeMillis());
                likeEntity.setUserId(users[0]);
                result.add(likeEntity);
            }
            if(new Random().nextInt(2) == 1){
                LikeEntity likeEntity = new LikeEntity();
                likeEntity.setPostId(posts.get(image.getId()).getId());
                likeEntity.setTimestamp(System.currentTimeMillis());
                likeEntity.setUserId(users[1]);
                result.add(likeEntity);
            }
        }
        return result;
    }

    private List<CommentEntity> generateRandomComments(List<ImageEntity> imageList) {
        List<CommentEntity> result = new ArrayList<>();
        Map<Integer, PostEntity> posts = new HashMap<>();
        for(ImageEntity imageEntity : imageList){
            PostEntity postEntity = context.getBean(PostsDao.class).findByImageId(imageEntity.getId());
            if(postEntity != null){
                posts.put(postEntity.getImageId(), postEntity);
            }
        }
        for(ImageEntity image : imageList){
            int upperLimit = new Random().nextInt(3);
            for(int i = 0; i < upperLimit; i++){
                CommentEntity entity = new CommentEntity();
                entity.setTimestamp(System.currentTimeMillis() - i * 10);
                entity.setUserId(users[new Random().nextInt(2)]);
                entity.setPostId(posts.get(image.getId()).getId());
                entity.setContent("Random comment " + i);
                result.add(entity);
            }
        }
        return result;
    }

    private List<PostEntity> generatePostList(List<ImageEntity> imageList) {
        List<PostEntity> result = new ArrayList<>();
        for(ImageEntity image : imageList){
            PostEntity entity = new PostEntity();
            entity.setImageId(image.getId());
            entity.setText("this is comment for pic " + image.getId());
            entity.setTimestamp(System.currentTimeMillis());
            entity.setUserId(image.getUserId());
            result.add(entity);
        }
        return result;
    }

    private ImageEntity uploadRandomPic(int index) {

        String[] picture = {ROOT_IMAGES + "flower.png", ROOT_IMAGES + "lena.png"};
        int userId = users[new Random().nextInt(2)];
        String picturePath = picture[new Random().nextInt(2)];
        long timestamp = System.currentTimeMillis() - index * 1000L;
        String fileName = "" + timestamp + ".png";
        String filePath = ROOT_IMAGES + userId + "//";
        File dest = new File(filePath + fileName);
        mkdirs(filePath, dest);
        try {
            thumbnailService.generateThumbnail(new File(picturePath), userId, timestamp);
            copyFile(new File(picturePath), dest);
            return Utils.getImageEntity(userId, timestamp, dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}
