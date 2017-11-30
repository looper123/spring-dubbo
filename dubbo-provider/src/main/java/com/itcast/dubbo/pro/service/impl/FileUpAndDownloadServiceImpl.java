package com.itcast.dubbo.pro.service.impl;

import com.itcast.dubbo.ser.service.FileUpAndDownloadService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zebon lu on 2017/5/19.
 * 文件上传下载服务类
 */
public class FileUpAndDownloadServiceImpl  implements FileUpAndDownloadService {

    /**
     * save file by transferTo method in MutipartFile class
     * @param multipartFile
     * @param request
     * @param filePath
     * @return
     */
    public String uploadByTransferTo(MultipartFile multipartFile , HttpServletRequest request,String filePath){
        if (multipartFile != null) {
            //get files suffix
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            //filePath+fileName = the complex file Name
            String absolutePath = getAndSetAbsolutePath(request, filePath, suffix);
            //return relative Path
            String relativePath = getRelativePath(filePath, suffix);
            try {
                //切换成windows的分隔符
                File file = new File(absolutePath.replace("/","\\"));
                //save file
                multipartFile.transferTo(file);
                //return relative Path
                return relativePath;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }


    /**
     * save file by io stream
     * @param request
     * @param filePath
     * @param multipartFile
     * @return
     */
    public String uploadByStream(HttpServletRequest request, MultipartFile multipartFile, String filePath){
        if (multipartFile != null) {
            //get files suffix
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            //filePath+fileName  = complex file Name
            String absolutePath = getAndSetAbsolutePath(request, filePath, suffix);
            //return relative Path
            String relativePath = getRelativePath(filePath, suffix);
            try {
                InputStream inputStream = multipartFile.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);
                byte buffer[] = new byte[4096]; //create a buffer
                long fileSize = multipartFile.getSize();
                if (fileSize <= buffer.length) {//if fileSize < buffer
                    buffer = new byte[(int) fileSize];
                }
                int line = 0;
                while ((line = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, line);
                }
                fileOutputStream.close();
                inputStream.close();
                return relativePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            return null;
        return null;

    }


    //---------------------------------------spilit line  ----------------------------------------
    //return server absolute path（real path）
    public static String getServerPath(HttpServletRequest request, String filePath) {
        return request.getSession().getServletContext().getRealPath(filePath);
    }

    //return a dir that named date of today ; example:20160912
    public static String getDataPath() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    //check if the path has exist if not create it
    public static void checkDir(String savePath) {
        File dir = new File(savePath);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
    }

    //return an UUID Name parameter (suffix cover '.') example： ".jpg"、".txt"
    public static String getUUIDName(String suffix) {
        return UUID.randomUUID().toString() + suffix;// make new file name
    }

    //return server absolute path（real path） the style is  “server absolute path/DataPath/UUIDName”filePath example "/files/Upload"
    public static String getAndSetAbsolutePath(HttpServletRequest request, String filePath, String suffix) {
        String savePath = getServerPath(request, filePath) + File.separator + getDataPath() + File.separator;//example:F:/qixiao/files/Upload/20160912/
        checkDir(savePath);//check if the path has exist if not create it
        return savePath + getUUIDName(suffix);
    }

    //get the relative path of files style is “/filePath/DataPath/UUIDName”filePath example "/files/Upload"
    public static String getRelativePath(String filePath, String suffix) {
        return filePath + File.separator + getDataPath() + File.separator + getUUIDName(suffix);//example:/files/Upload/20160912/
    }
}
