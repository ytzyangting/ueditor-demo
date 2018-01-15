package com.example.demo;

import lombok.Builder;
import lombok.Data;

/**
 * Ueditor上传文件后返回格式
 * Created by yangting on 2018/1/6.
 */
@Data
@Builder
public class UEditorUploadResponse {

    /**
     * 状态，SUCCESS为成功，其他为失败信息
     */
    private String state;

    /**
     * 上传文件后生成的文件路径
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 原始文件名
     */
    private String original;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件名
     */
    private String title;

    /**
     * 文件大小，单位B
     */
    private Long size;
}
