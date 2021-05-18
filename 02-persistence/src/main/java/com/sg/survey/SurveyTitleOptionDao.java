package com.sg.survey;

import com.sg.survey.submit.SubmitSummaryModel;
import com.sg.survey.title.TitleInfoModel;
import com.sg.survey.title.TitleModel;
import com.sg.survey.title.TitleSummaryModel;
import com.sg.survey.title.option.OptionModel;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * Created by jiuge on 2020/5/24.
 */
public interface SurveyTitleOptionDao {

    SurveyTitleOptionModel<TitleInfoModel> getSurveyTitleOptionModelById(String surveyId);

    SurveyTitleOptionModel getSurveyTitleOptionModel(@Param("surveyId") String surveyId,@Param("type") String type);

    SurveyTitleOptionStatisticsModel getSurveyStatistics(String surveyId);

    SurveyTitleOptionSummaryModel getSurveySubmitSummary(String surveyId);

    List<SurveyTitleOptionSubmitModel> getSurveySubmitDetailList(String surveyId);

    List<SubmitSummaryModel> getSubmitSummaryList(String surveyId);

    int getSubmitsCount(String surveyId);
}
