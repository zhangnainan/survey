package com.sg.survey.submit;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SurveySubmitDao {

    public int insertSurveySubmit(SurveySubmitModel surveySubmitModel);

    public int insertSurveySubmitList(List<SurveySubmitModel> surveySubmitModelList);

    public List<SurveySubmitModel> getSurveySubmitsById(String surveyId);

    public List<SurveySubmitModel> getSurveySubmitsByIdAndWxNickname(@Param("surveyId") String surveyId,@Param("wxNickname") String wxNickname);

    public int deleteSurveySubmitsBySurveyId(String surveyId);

    public int deleteSurveySubmitById(String id);
}
