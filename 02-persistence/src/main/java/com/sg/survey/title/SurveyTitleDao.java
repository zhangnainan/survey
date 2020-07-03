package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SurveyTitleDao {

    public int insertSurveyTitle(SurveyTextTitleModel surveyTextTitleModel);

    public int insertSurveyTitleList(List<SurveyTitleModel> surveyTitleModelList);

    public int deleteSurveyTitleById(String id);

    public int deleteSurveyTitleBySurveyId(String surveyId);

    public int updateSurveyTitleModel(SurveyTitleModel surveyTitleModel);

    public List<SurveyTitleModel> querySurveyTitleBySurveyId(String surveyId);
}
