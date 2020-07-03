package com.sg.survey.title;

import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
public interface SurveySingleTitleDao {

    public int insertSurveySingleTitle(SurveySingleTitleModel surveySingleTitleModel);

    public int insertSurveySingleTitleList(List<SurveySingleTitleModel> surveySingleTitleModelList);

    public int deleteSurveySingleTitleById(String id);

    public int deleteSurveySingleTitleBySurveyId(String surveyId);

    public List<SurveySingleTitleModel> querySurveySingleTitleBySurveyId(String surveyId);
}
