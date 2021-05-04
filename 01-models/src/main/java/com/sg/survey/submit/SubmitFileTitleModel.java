package com.sg.survey.submit;

public class SubmitFileTitleModel extends SubmitTitleModel {

    private String fileName;

    public SubmitFileTitleModel(){

    }

    public SubmitFileTitleModel(String id,String submitId,String titleId,String fileName){
        this.id = id;
        this.submitId = submitId;
        this.titleId = titleId;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
