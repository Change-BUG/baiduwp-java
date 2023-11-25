package com.example.baiduwpjava.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiDuFile {

    /**
     * id
     */
    private String fs_id;

    /**
     * 文件名
     */
    private String server_filename;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件类型
     */
    private Integer category;

    /**
     * 修改时间
     */
    private Integer server_ctime;

}
