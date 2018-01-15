package com.example.demo.service;

import com.example.demo.PathGen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件上传Service
 * Created by yangting on 2018/1/6.
 */
@Service
public class UploadService {

    /**
     * 文件上传保存路径
     */
    @Value("${uploadPath}")
    private String baseDir;

    public String saveFile(String originName, byte[] content) {
        String path = PathGen.getPath(originName);
        File f = new File(baseDir + path);
        if (!f.exists()) {
            try {
                File fileParent = f.getParentFile();
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            StreamUtils.copy(content, new FileOutputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public byte[] getFile(String path) {
        File f = new File(baseDir + path);
        byte[] content = null;
        try {
            content = StreamUtils.copyToByteArray(new FileInputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
