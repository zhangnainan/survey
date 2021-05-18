package com.sg.survey;

import com.sg.survey.title.TitleInfoModel;
import com.sg.survey.title.TitleModel;
import com.sg.survey.title.option.OptionModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by jiuge on 2020/5/9.
 */
public interface SurveyService {

    /**
     * 获取所有的问卷列表
     *
     * @return
     */
    Result getSurveyModelListByCreator(String creator);

    /**
     * 获取问卷或知识竞赛列表
     *
     * @param creator
     * @param surveyType
     * @return
     */
    Result getSurveyModelListByCreatorAndType(String creator, String surveyType);

    /**
     * 单纯获取调查问卷信息
     *
     * @param surveyId
     * @return
     */
    Result getSurveyInfo(String surveyId);

    /**
     * 获取问卷详细信息
     *
     * @param surveyId
     * @return
     */
    Result getSurveyTitleOptionModel(String surveyId);

    /**
     *
     * @param surveyId
     * @param wxNickname
     * @param wxOpenId
     * @return
     */
    Result getSurveyTitleOptionModel(String surveyId, String wxNickname, String wxOpenId);


    /**
     * 提交问卷调查填写结果
     *
     * @param surveyTitleOptionModel
     * @return
     */
    Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel);

    /**
     * 提交问卷调查填写结果（如果该微信昵称上次有提交过，则进行更新操作）
     *
     * @param surveyTitleOptionModel
     * @param wxNickname
     * @param wxOpenId
     * @return
     */
    Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel, String wxNickname, String wxOpenId);

    /**
     * 提交竞赛答卷结果
     *
     * @param titleModelList
     * @param surveyId
     * @param timeCount
     * @param wxNickname
     * @param wxOpenId
     * @return
     */
    Result submitContestTitleList(List<TitleInfoModel> titleModelList, String surveyId, String submitter, String timeCount, String wxNickname, String wxOpenId);


    /**
     * 获取问卷结果汇总
     *
     * @param surveyId
     * @return
     */
    Result getSurveySummary(String surveyId);

    /**
     * 获取问卷结果汇总(新版)
     *
     * @param surveyId
     * @return
     */
    Result getSurveySummaryNew(String surveyId);

    /**
     * 获取答卷详情
     *
     * @param surveyId
     * @return
     */
    Result getSurveySubmitDetailList(String surveyId);

    /**
     * 获取答卷详情(新版)
     *
     * @param surveyId
     * @return
     */
    Result getSurveySubmitDetailListNew(String surveyId);

    /**
     * 问卷按姓名进行统计
     *
     * @param surveyId
     * @param titleId
     * @return
     */
    Result getSurveyByNameStatistics(String surveyId, String titleId);

    /**
     * 问卷按姓名进行统计(新版)
     *
     * @param surveyId
     * @param titleId
     * @return
     */
    Result getSurveyByNameStatisticsNew(String surveyId, String titleId);

    /**
     * 问卷创建
     *
     * @param surveyModel
     * @return
     */
    Result saveSurvey(SurveyModel surveyModel);

    /**
     * 更新surveyModel
     *
     * @param surveyModel
     * @return
     */
    Result updateSurvey(SurveyModel surveyModel);

    /**
     * 删除Survey
     *
     * @param surveyId
     * @return
     */
    Result deleteSurvey(String surveyId);

    /**
     * 更新survey的时间设置
     *
     * @param surveyModel
     * @return
     */
    Result updateSurveyTimeSetting(SurveyModel surveyModel);

    /**
     * 清空survey数据
     *
     * @param surveyId
     * @return
     */
    Result clearSurvey(String surveyId);

    /**
     * 创建Excel工作表
     *
     * @param surveyId
     * @param sortCols
     * @param titleModelList
     * @return
     */
    XSSFWorkbook createWorkbook(String surveyId, int sortCols, List<TitleModel> titleModelList);


    /**
     * 竞赛题目导入
     *
     * @param file
     * @return
     */
    Result contestTitleImport(MultipartFile file, String surveyId);
}
