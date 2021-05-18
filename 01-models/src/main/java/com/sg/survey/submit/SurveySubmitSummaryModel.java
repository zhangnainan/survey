package com.sg.survey.submit;

import java.util.List;

public class SurveySubmitSummaryModel extends SurveySubmitModel {

    private List<SubmitTitleAnswerModel> submitTitleAnswerModelList;

    public List<SubmitTitleAnswerModel> getSubmitTitleAnswerModelList(){
        return submitTitleAnswerModelList;
    }

    public void setSubmitTitleAnswerModelList(List<SubmitTitleAnswerModel> submitTitleAnswerModelList) {
        this.submitTitleAnswerModelList = submitTitleAnswerModelList;
    }
}
