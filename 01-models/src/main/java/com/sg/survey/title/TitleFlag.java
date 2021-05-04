package com.sg.survey.title;

public enum TitleFlag {

    Single("ST"),Multiple("MT"),Text("TT");

    private String flag;

    private TitleFlag(String flag){
        this.flag = flag;
    }

    public String getFlag(){
        return flag;
    }
}
