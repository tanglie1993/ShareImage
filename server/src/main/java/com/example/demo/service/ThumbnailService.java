package com.example.demo.service;

import com.example.demo.constants.FileConstants;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

@Service
public class ThumbnailService {

    public static final int WIDTH = 100 ;
    public static final int HEIGHT = 100 ;

    public void generateThumbnail(MultipartFile file, int userId, long timeStamp) {
        try {
            String thumbnailAddress = FileConstants.getThumbnailAddress(userId, timeStamp);
            if (!new File(thumbnailAddress).getParentFile().exists()) {
                new File(thumbnailAddress).getParentFile().mkdirs();
            }
            Thumbnails.of(file.getInputStream()).size(WIDTH, HEIGHT).toFile(thumbnailAddress);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
