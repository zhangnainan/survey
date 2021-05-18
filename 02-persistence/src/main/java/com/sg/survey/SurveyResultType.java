package com.sg.survey;

public enum SurveyResultType {

    Origin("origin"),Info("info"),Summary("summary"),Statistics("statistics");

    private String type;

    private SurveyResultType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}
