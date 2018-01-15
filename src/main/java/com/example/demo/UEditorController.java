package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * UEditor配置与上传接口
 * Created by yangting on 2018/1/6.
 */
@RestController
@RequestMapping("/ueditor")
public class UEditorController {

    @Autowired
    private UploadService uploadService;

    /**
     * UEditor配置
     * @return
     */
    @GetMapping("/config")
    public JSONObject config() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:config.json");
        InputStream inputStream ;
        try {
            inputStream = new FileInputStream(file);
            return JSON.parseObject(inputStream,JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * UEditor文件上传
     * @param upfile
     * @return
     */
    @PostMapping(value = "/uploadFile")
    public UEditorUploadResponse imgUpload3(MultipartFile upfile) throws IOException {
        String path = uploadService.saveFile(upfile.getOriginalFilename(), upfile.getBytes());
        // 上传开始
        return UEditorUploadResponse.builder()
                .state("SUCCESS")
                .url("http://localhost:8080" + path)
                .original(upfile.getOriginalFilename())
                .name(upfile.getName())
                .title(upfile.getOriginalFilename())
                .build();
    }

}
