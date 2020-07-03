package com.sg.survey.submit;

import java.util.List;

/**
 * Created by jiuge on 2020/5/29.
 */
public class SubmitSummaryModel {

    private String id;
    private String surveyId;
    private String submitter;
    List<SubmitTitleAnswerModel> submitTitleAnswerModelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public List<SubmitTitleAnswerModel> getSubmitTitleAnswerModelList() {
        return submitTitleAnswerModelList;
    }

    public void setSubmitTitleAnswerModelList(List<SubmitTitleAnswerModel> submitTitleAnswerModelList) {
        this.submitTitleAnswerModelList = submitTitleAnswerModelList;
    }
}
