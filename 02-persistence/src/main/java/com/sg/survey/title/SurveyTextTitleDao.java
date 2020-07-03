package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
public interface SurveyTextTitleDao {

    public int insertSurveyTextTitle(SurveyTextTitleModel surveyTextTitleModel);

    public int insertSurveyTextTitleList(List<SurveyTextTitleModel> surveyTextTitleModelList);

    public int deleteSurveyTextTitleById(String id);

    public int deleteSurveyTextTitleBySurveyId(String surveyId);

    public int updateSurveyTextTitleModel(SurveyTextTitleModel surveyTextTitleModel);

    public List<SurveyTextTitleModel> querySurveyTextTitleBySurveyId(String surveyId);

}
