package com.sg.survey.title;

/**
 * Created by jiuge on 2020/5/15.
 */
public enum TitleType {
    SingleTitle("0"),MultipleTitle("1"),Text("2"),Image("3");

    private String val;

    private TitleType(String val){
        this.val = val;
    }

    public String getVal(){
        return this.val;
    }
}
