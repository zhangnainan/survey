package com.sg.survey.title;

public enum AnswerFlag {
    True("T");

    private String flag;
    private AnswerFlag(String flag){
        this.flag = flag;
    }

    public String getFlag(){
        return this.flag;
    }
}
