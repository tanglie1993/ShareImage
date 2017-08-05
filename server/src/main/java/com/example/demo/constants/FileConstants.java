package com.example.demo.constants;

public class FileConstants {

        public static final String ROOT_IMAGES = "d://images//";
//    public static final String ROOT_IMAGES = "//root//images//";

    public static String getThumbnailAddress(int userId, long timeStamp){
        return FileConstants.ROOT_IMAGES + userId + "//thumb_" + timeStamp;
    }
}
