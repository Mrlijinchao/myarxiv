package com.myarxiv.myarxiv.common;

import java.io.File;

public class FileUtils {

    public static void deleteFile(String filePath){
        try{
            File file = new File(filePath);
            if(file.delete()){
                System.out.println(file.getName() + " 文件已被删除！");
            }else{
                System.out.println("文件删除失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
