package com.sg.survey.submit;

/**
 * Created by jiuge on 2020/5/25.
 */
public class SubmitOptionModel extends SubmitTitleModel {
    private String optionId;


    public SubmitOptionModel(){

    }

    public SubmitOptionModel(String id, String submitId, String titleId, String optionId){
        this.id = id;
        this.submitId = submitId;
        this.titleId = titleId;
        this.optionId = optionId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}
