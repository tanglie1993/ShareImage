package com.example.demo.controller;

import com.example.demo.dao.ImagesDao;
import com.example.demo.dao.PostsDao;
import com.example.demo.dao.UsersDao;
import com.example.demo.entity.ImageEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UserEntity;
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
    ImagesDao imagesDao;
    @Autowired
    WebApplicationContext context;
    @Autowired
    ThumbnailService thumbnailService;

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
        List<ImageEntity> entityList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            entityList.add(uploadRandomPic(i));
        }
        imagesDao.save(entityList);
        return "";
    }

    private ImageEntity uploadRandomPic(int index) {
        int[] users = {42, 43};
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
