package com.conner.shop.commons.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.conner.shop.commons.constant.ConstantUtils.UPLOAD_ACCESS_PATH;
import static com.conner.shop.commons.constant.ConstantUtils.UPLOAD_PATH;

public class FileReceive {

    public static String receive( MultipartFile multipartFile, HttpServletRequest request) {
        // 获取文件后缀
        String fileName = multipartFile.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));

        // target中文件存放路径
        String filePath = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);

        // 本机中文件存放路径
        String webPath = UPLOAD_ACCESS_PATH;


        // 判断路径是否存在，不存在则创建文件夹
        File file = new File(webPath);
        if (!file.exists()) {
            file.mkdir();
        }

        // 将文件写入目标
        file = new File(webPath, UUID.randomUUID() + fileSuffix);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回文件完整路径
        String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return serverPath +"/upload/" + file.getName();
    }
}
