package com.example.demo.util;

import com.example.demo.dao.ImagesDao;
import com.example.demo.entity.ImageEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Utils {
    public static void mkdirs(String filePath, File dest) {
        if(!new File(filePath).exists()){
            new File(filePath).mkdirs();
        }
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
    }

    public static ImageEntity getImageEntity(Integer userId, Long timestamp, File dest) throws IOException {
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
        return entity;
    }
}
