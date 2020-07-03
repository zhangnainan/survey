package com.sg.survey;

import com.sg.survey.submit.SubmitSummaryModel;
import com.sg.survey.title.TitleInfoModel;
import com.sg.survey.title.TitleStatisticsModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/24.
 */
public interface SurveyTitleOptionDao {

    public SurveyTitleOptionModel<TitleInfoModel> getSurveyTitleOptionModelById(String surveyId);

    public SurveyTitleOptionStatisticsModel getSurveyStatistics(String surveyId);

    public SurveyTitleOptionSummaryModel getSurveySubmitSummary(String surveyId);

    public List<SurveyTitleOptionSubmitModel> getSurveySubmitDetailList(String surveyId);

    public List<SubmitSummaryModel> getSubmitSummaryList(String surveyId);

    public int getSubmitsCount(String surveyId);
}
