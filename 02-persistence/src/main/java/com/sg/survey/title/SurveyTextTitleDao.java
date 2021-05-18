package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
public interface SurveyTextTitleDao {

    int insertSurveyTextTitle(SurveyTextTitleModel surveyTextTitleModel);

    int insertSurveyTextTitleList(List<SurveyTextTitleModel> surveyTextTitleModelList);

    int deleteSurveyTextTitleById(String id);

    int deleteSurveyTextTitleBySurveyId(String surveyId);

    int updateSurveyTextTitleModel(SurveyTextTitleModel surveyTextTitleModel);

    List<SurveyTextTitleModel> querySurveyTextTitleBySurveyId(String surveyId);

}
