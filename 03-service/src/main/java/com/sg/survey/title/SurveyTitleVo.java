package com.sg.survey.title;

import com.sg.survey.title.option.OptionModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/20.
 */
public class SurveyTitleVo {

    private SurveyTitleModel surveyTitleModel;
    private List<OptionModel> optionModelList;


    public SurveyTitleModel getSurveyTitleModel() {
        return surveyTitleModel;
    }

    public void setSurveyTitleModel(SurveyTitleModel surveyTitleModel) {
        this.surveyTitleModel = surveyTitleModel;
    }

    public List<OptionModel> getOptionModelList() {
        return optionModelList;
    }

    public void setOptionModelList(List<OptionModel> optionModelList) {
        this.optionModelList = optionModelList;
    }
}
