package com.sg.survey.submit;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SurveySubmitDao {

    public int insertSurveySubmit(SurveySubmitModel surveySubmitModel);

    public int insertSurveySubmitList(List<SurveySubmitModel> surveySubmitModelList);

    public List<SurveySubmitModel> querySurveySubmitListBySurveyId(String surveyId);

    public int deleteSurveySubmitsBySurveyId(String surveyId);
}
