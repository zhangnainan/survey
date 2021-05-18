package com.sg.survey.submit;

import com.sg.survey.Result;

import java.util.List;

public interface SubmitService {

    Result getSurveySubmitList(String surveyId, String wxOpenId);

    Result getContestRankPage(String surveyId, int pageSize, int start);

    Result getSubmitCount(String surveyId);

    List<SurveySubmitSummaryModel> getSurveySubmitSummaryModelList(String surveyId);
}
