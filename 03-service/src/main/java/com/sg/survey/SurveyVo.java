package com.sg.survey;

import com.sg.survey.title.SurveyTitleVo;

import java.util.List;

/**
 * Created by jiuge on 2020/5/20.
 */
public class SurveyVo {

    private SurveyModel surveyModel;

    private List<SurveyTitleVo> surveyTitleVoList;


    public SurveyModel getSurveyModel() {
        return surveyModel;
    }

    public void setSurveyModel(SurveyModel surveyModel) {
        this.surveyModel = surveyModel;
    }

    public List<SurveyTitleVo> getSurveyTitleVoList() {
        return surveyTitleVoList;
    }

    public void setSurveyTitleVoList(List<SurveyTitleVo> surveyTitleVoList) {
        this.surveyTitleVoList = surveyTitleVoList;
    }
}
