package com.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {

    public static String generateFilePath(String fileName){
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");  //设置格式
        String datePath = sdf.format(new Date()); //new date() 获取现在时间   把时间戳转换成上面设置的格式
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");  //返回子串最后一次出现的位置
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);  //返回从索引位置开始截取的子串
        String string = new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
        return string;
    }
}