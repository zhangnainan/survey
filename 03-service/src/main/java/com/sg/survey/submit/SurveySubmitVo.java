package com.sg.survey.submit;

import com.sg.survey.SurveyModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/20.
 */
public class SurveySubmitVo {

    private SurveyModel surveyModel;
    private List<SubmitTitleVo> submitTitleVoList;

    public SurveyModel getSurveyModel() {
        return surveyModel;
    }

    public void setSurveyModel(SurveyModel surveyModel) {
        this.surveyModel = surveyModel;
    }

    public List<SubmitTitleVo> getSubmitTitleVoList() {
        return submitTitleVoList;
    }

    public void setSubmitTitleVoList(List<SubmitTitleVo> submitTitleVoList) {
        this.submitTitleVoList = submitTitleVoList;
    }
}
