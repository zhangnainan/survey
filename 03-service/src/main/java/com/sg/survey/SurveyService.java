package com.sg.survey;

import com.sg.survey.submit.SurveySubmitVo;

/**
 * Created by jiuge on 2020/5/9.
 */
public interface SurveyService {

    /**
     * 获取所有的问卷列表
     *
     * @return
     */
    public Result getSurveyModelList();

    /**
     * 获取问卷详细信息
     *
     * @param surveyId
     * @return
     */
    public Result getSurveyTitleOptionModel(String surveyId);


    /**
     * 提交问卷调查填写结果
     *
     * @param surveyTitleOptionModel
     * @return
     */
    public Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel);

    /**
     * 获取问卷结果汇总
     *
     * @param surveyId
     * @return
     */
    public Result getSurveySummary(String surveyId);


    /**
     * 获取答卷详情
     *
     * @param surveyId
     * @return
     */
    public Result getSurveySubmitDetailList(String surveyId);


    /**
     * 问卷按姓名进行统计
     *
     * @param surveyId
     * @param titleId
     * @return
     */
    public Result getSurveyByNameStatistics(String surveyId, String titleId);


    /**
     * 问卷创建
     *
     * @param surveyModel
     * @return
     */
    public Result saveSurvey(SurveyModel surveyModel);

    /**
     * 更新surveyModel
     *
     * @param surveyModel
     * @return
     */
    public Result updateSurvey(SurveyModel surveyModel);

    /**
     * 删除Survey
     *
     * @param surveyId
     * @return
     */
    public Result deleteSurvey(String surveyId);

//    /**
//     * 获取问卷的详细信息
//     *
//     * @param surveyId
//     * @return
//     */
//    public Result getSurveyVo(String surveyId);
//
//
//    /**
//     * 提交问卷答案
//     *
//     * @param surveySubmitVo
//     * @return
//     */
//    public Result submitSurveyVo(SurveySubmitVo surveySubmitVo);
//
//    /**
//     * 获取问卷答案列表
//     *
//     * @param surveyId
//     * @return
//     */
//    public Result getSurveySubmitVoList(String surveyId);


}
