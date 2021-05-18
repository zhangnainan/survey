package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SurveyTitleDao {

    int insertSurveyTitle(SurveyTextTitleModel surveyTextTitleModel);

    int insertSurveyTitleList(List<SurveyTitleModel> surveyTitleModelList);

    int deleteSurveyTitleById(String id);

    int deleteSurveyTitleBySurveyId(String surveyId);

    int updateSurveyTitleModel(SurveyTitleModel surveyTitleModel);

    List<SurveyTitleModel> querySurveyTitleBySurveyId(String surveyId);
}
