package com.example.demo.controller;

import com.example.demo.constants.FileConstants;
import com.example.demo.dao.ImagesDao;
import com.example.demo.entity.ImagesEntity;
import com.example.demo.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    ImagesDao imagesDao;

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
            file.transferTo(dest);
            thumbnailService.generateThumbnail(file, userId, timestamp);
            saveToDatabase(userId, dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    private void saveToDatabase(@RequestParam(value = "user_id", required = true) Integer userId, File dest) throws IOException {
        ImagesEntity entity = new ImagesEntity();
        entity.setUserId(userId);
        entity.setTimestamp(System.currentTimeMillis());
        String[] splitedName = dest.getName().split("\\.");
        if(splitedName.length > 0){
            entity.setFormat(splitedName[splitedName.length - 1]);
        }else{
            entity.setFormat("unknown");
        }
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
        entity.setWidth(sourceImg.getWidth());
        entity.setHeight(sourceImg.getHeight());
        entity.setBytes((double) dest.length());
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
        List<ImagesEntity> list = new ArrayList<>();
        list.addAll(imagesDao.findByUserId(userId));
        result.put("list", list);
        return result;
    }

    @RequestMapping(value = "/image/{user_id}/{timestamp}.{format}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable ("user_id") Integer userId,
                                     @PathVariable ("timestamp") Long timestamp,
                                     @PathVariable ("format") String format) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" +
                    Paths.get(FileConstants.ROOT_IMAGES + userId , "" + timestamp + "." + format).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
