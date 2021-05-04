package com.sg.survey.title;

public enum NameColumn {

    YES("1"),NO("0");

    private String val;

    private NameColumn(String val){
        this.val = val;
    }

    public String getVal(){
        return this.val;
    }
}
