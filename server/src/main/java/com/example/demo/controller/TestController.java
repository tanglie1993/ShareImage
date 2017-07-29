package com.example.demo.controller;

import com.example.demo.dao.ImagesDao;
import com.example.demo.entity.ImagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    ImagesDao imagesDao;

    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file,
                         @RequestParam(value = "user_id", required = true) Integer userId,
                         @RequestParam(value = "timestamp", required = false) Integer timestamp) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        String fileName = getFileName(timestamp);
        String filePath = "//root//images//" + userId + "//";
//        String filePath = "d://" + userId + "//";
        File dest = new File(filePath + fileName);
        mkdirs(filePath, dest);
        try {
            file.transferTo(dest);
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

    private String getFileName(@RequestParam(value = "timestamp", required = false) Integer timestamp) {
        String fileName;
        if(timestamp != null){
            fileName = "" + timestamp + ".png";
        }else{
            fileName = "" + System.currentTimeMillis() + ".png";
        }
        return fileName;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void download(@RequestParam ("user_id") Integer userId,
                         @RequestParam ("timestamp") Long timestamp,
                         HttpServletResponse res) {
        String fileName = "//root//images//" + userId + "//" + timestamp + ".png";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/imageList", method = RequestMethod.GET)
    public Map<String, Object> getImageList(@RequestParam ("user_id") Integer userId) {
        Map<String, Object> result = new HashMap<>();
        List<Long> list = new ArrayList<>();
        for(ImagesEntity entity : imagesDao.findByUserId(userId)){
            list.add(entity.getTimestamp());
        }
        result.put("list", list);
        return result;
    }
}
