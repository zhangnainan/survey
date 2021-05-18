package com.sg.survey.submit;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SurveySubmitDao {

    int insertSurveySubmit(SurveySubmitModel surveySubmitModel);

    int insertSurveySubmitList(List<SurveySubmitModel> surveySubmitModelList);

    List<SurveySubmitModel> getSurveySubmitsById(String surveyId);

    List<SurveySubmitModel> getContestRankPage(@Param("surveyId") String surveyId,@Param("pageSize") int pageSize,@Param("start") int start);

    List<SurveySubmitSummaryModel> getSurveySubmitSummaryList(String surveyId);

    List<SubmitTitleAnswerModel> getSubmitTitleAnswerListBySubmitIds(List<SurveySubmitSummaryModel> submitSummaryModels);
    /**
    List<SubmitTitleAnswerModel> getSubmitsSingleTitleAnswerList(List<SurveySubmitSummaryModel> submitSummaryModels);

    List<SubmitTitleAnswerModel> getSubmitsMultipleTitleAnswerList(List<SurveySubmitSummaryModel> submitSummaryModels);

    List<SubmitTitleAnswerModel> getSubmitsTextTitleAnswerList(List<SurveySubmitSummaryModel> submitSummaryModels);

    List<SubmitTitleAnswerModel> getSubmitsFileTitleAnswerList(List<SurveySubmitSummaryModel> submitSummaryModels);
    **/
    int getSubmitCount(String surveyId);

    List<SurveySubmitModel> getSurveySubmitsByIdAndWxNickname(@Param("surveyId") String surveyId,@Param("wxNickname") String wxNickname);

    List<SurveySubmitModel> getSurveySubmitsByIdAndWxOpenId(@Param("surveyId") String surveyId,@Param("wxOpenId") String wxOpenId);

    int deleteSurveySubmitsBySurveyId(String surveyId);

    int deleteSurveySubmitById(String id);
}
