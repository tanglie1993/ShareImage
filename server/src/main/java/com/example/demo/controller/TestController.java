package com.example.demo.controller;

//import com.example.demo.dao.ImagesDao;
import com.example.demo.dao.ImagesDao;
import com.example.demo.entity.ImagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    ImagesDao imagesDao;

    @RequestMapping("/h")
    public String hello() {
        return "Hello";
    }

    @RequestMapping("/w")
    public String world() {
        return "World";
    }

    //文件上传相关代码
    @RequestMapping(value = "upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("user_id") Integer userId) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = "" + System.currentTimeMillis() + ".png";
        String filePath = "//root//images//" + userId + "//";
        if(!new File(filePath).exists()){
            new File(filePath).mkdirs();
        }
        File dest = new File(filePath + fileName);
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            ImagesEntity entity = new ImagesEntity();
            entity.setUserId(userId);
            entity.setTimestamp(System.currentTimeMillis());
            entity.setFormat("png");
            imagesDao.save(entity);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
//
//    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
//    public void testDownload(@RequestParam ("user_id") Integer userId,
//                             HttpServletResponse res) {
//        String fileName = "//root//images//" + userId + "//" + imagesDao.findByUserId(userId).get(0).getTimestamp() + ".png";
//        res.setHeader("content-type", "application/octet-stream");
//        res.setContentType("application/octet-stream");
//        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        byte[] buff = new byte[1024];
//        BufferedInputStream bis = null;
//        OutputStream os = null;
//        try {
//            os = res.getOutputStream();
//            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
//            int i = bis.read(buff);
//            while (i != -1) {
//                os.write(buff, 0, buff.length);
//                os.flush();
//                i = bis.read(buff);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("success");
//    }
}
