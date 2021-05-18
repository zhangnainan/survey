package com.sg.survey;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/5/7.
 */
public interface SurveyDao {

    int saveSurvey(SurveyModel surveyModel);

    int updateSurvey(SurveyModel surveyModel);

    int updateSurveyTimeSetting(SurveyModel surveyModel);

    int deleteSurvey(String surveyId);

    SurveyModel queryById(String id);

    List<SurveyModel> queryByName(String surveyName);

    List<SurveyModel> queryByNameAndType(@Param("surveyName") String surveyName, @Param("surveyType") String surveyType);

    List<SurveyModel> queryBySurveyNameAndCreator(@Param("surveyName") String surveyName, @Param("creator") String creator);

    List<SurveyModel> queryBySurveyNameAndTypeAndCreator(@Param("surveyName") String surveyName, @Param("creator") String creator, @Param("surveyType") String surveyType);

    List<SurveyModel> queryAll();

    List<SurveyModel> querySurveyListByCreator(String creator);

    List<SurveyModel> querySurveyListByCreatorAndType(@Param("creator") String creator, @Param("surveyType") String surveyType);

    int getSubmitsCountById(String surveyId);

}
