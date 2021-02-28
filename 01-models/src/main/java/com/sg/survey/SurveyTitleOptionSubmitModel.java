package com.sg.survey;

import com.sg.survey.title.TitleInfoModel;

/**
 * Created by jiuge on 2020/6/19.
 */
public class SurveyTitleOptionSubmitModel extends SurveyTitleOptionModel<TitleInfoModel> {

    private String submitId;


    public String getSubmitId() {
        return submitId;
    }

    public void setSubmitId(String submitId) {
        this.submitId = submitId;
    }


}
