package com.sg.survey.title;

/**
 * Created by jiuge on 2020/5/7.
 */
public enum RequiredFlag {
    True("1"),False("0");

    private String flag;

    private RequiredFlag(String flag){
        this.flag = flag;
    }

    public String getFlag(){
        return flag;
    }
}
