package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
public interface SurveySingleTitleDao {

    int insertSurveySingleTitle(SurveySingleTitleModel surveySingleTitleModel);

    int insertSurveySingleTitleList(List<SurveySingleTitleModel> surveySingleTitleModelList);

    int deleteSurveySingleTitleById(String id);

    int deleteSurveySingleTitleBySurveyId(String surveyId);

    List<SurveySingleTitleModel> querySurveySingleTitleBySurveyId(String surveyId);
}
