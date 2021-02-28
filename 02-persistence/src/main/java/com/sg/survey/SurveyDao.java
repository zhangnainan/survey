package com.sg.survey;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/5/7.
 */
public interface SurveyDao {

    public int saveSurvey(SurveyModel surveyModel);

    public int updateSurvey(SurveyModel surveyModel);

    public int updateSurveyTimeSetting(SurveyModel surveyModel);

    public int deleteSurvey(String surveyId);

    public SurveyModel queryById(String id);

    public List<SurveyModel> queryByName(String surveyName);

    public List<SurveyModel> queryBySurveyNameAndCreator(@Param("surveyName") String surveyName, @Param("creator") String creator);

    public List<SurveyModel> queryAll();

    public List<SurveyModel> querySurveyListByCreator(String creator);

    public int getSubmitsCountById(String surveyId);

}
