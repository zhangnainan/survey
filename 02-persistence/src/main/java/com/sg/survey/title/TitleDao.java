package com.sg.survey.title;

import com.sg.survey.submit.SubmitTitleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleDao {

    public List<TitleModel> getTitleModelList(@Param("surveyId") String surveyId,@Param("titleType") String titleType);

    public List<TitleModel> getAllTitleModelList(String surveyId);

    public List<TitleModel> queryTitleModel(@Param("surveyId") String surveyId, @Param("titleName") String titleName);

    public List<TitleModel> queryNameColumnBySurveyId(String surveyId);

    public List<SubmitTitleModel> querySubmitTitleList(@Param("titleId") String titleId,@Param("titleType") String titleType);

    public int saveTitleModel(TitleModel titleModel);

    public int updateTitleModel(TitleModel titleModel);

    public int deleteTitleModel(String titleId);

    public int deleteTitleModelList(List<TitleModel> titleModelList);

    public int updateTitleModelSequence(List<TitleModel> titleModelList);
}
