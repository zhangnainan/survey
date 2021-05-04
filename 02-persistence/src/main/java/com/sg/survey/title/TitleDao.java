package com.sg.survey.title;

import com.sg.survey.submit.SubmitTitleModel;
import com.sg.survey.title.option.OptionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleDao {

    public List<TitleModel> getTitleModelList(@Param("surveyId") String surveyId,@Param("titleType") String titleType);

    public int getTitleCount(String surveyId);

    public List<TitleModel> getAllTitleModelList(String surveyId);

    public List<TitleModel<OptionModel>> getTitlePage(@Param("surveyId") String surveyId,@Param("pageSize") int pageSize,@Param("start") int start);

    public List<TitleAnswerModel> getTitleAnswerPage(@Param("surveyId") String surveyId,@Param("pageSize") int pageSize,@Param("start") int start);

    public List<TitleModel> queryTitleModel(@Param("surveyId") String surveyId, @Param("titleName") String titleName);

    public List<TitleModel> queryNameColumnBySurveyId(String surveyId);

    public List<SubmitTitleModel> querySubmitTitleList(@Param("titleId") String titleId,@Param("titleType") String titleType);

    public int saveTitleModel(TitleModel titleModel);

    public int saveTitleModelList(List<TitleModel<OptionModel>> titleModelList);

    public int updateTitleModel(TitleModel titleModel);

    public int deleteTitleModel(String titleId);

    public int deleteTitleModelList(List<TitleModel> titleModelList);

    public int updateTitleModelSequence(List<TitleModel> titleModelList);
}
