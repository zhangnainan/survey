package com.sg.survey.util;

import com.sg.survey.os.OSInfo;

public class UploadUtil {

    public static String getUploadPath(){

        String realPath = "";

        if(OSInfo.isWindows()){
            realPath = "E:/survey/upload/imgs";
        }else{ // Linux
            realPath = "/root/survey/upload/imgs";
        }

        return realPath;
    }

    public static String getImageVisitUrl(){
        String imageVisitUrl = "";

        if(OSInfo.isWindows()){
            imageVisitUrl = "http://localhost:8080/survey/upload/imgs/";
        }else{ // Linux
            imageVisitUrl = "https://www.3steelspace.cn/survey/upload/imgs/";
        }

        return imageVisitUrl;
    }

}
