package com.sg.survey;

public enum SaveType {
    Create("create"),Update("update");

    private String saveType;

    private SaveType(String saveType){
        this.saveType = saveType;
    }

    public String getSaveType(){
        return this.saveType;
    }

}
