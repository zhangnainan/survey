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

    List<TitleModel> getTitleModelList(@Param("surveyId") String surveyId,@Param("titleType") String titleType);

    int getTitleCount(String surveyId);

    List<TitleModel> getAllTitleModelList(String surveyId);

    List<TitleModel<OptionModel>> getContestTitle(@Param("surveyId") String surveyId,@Param("answerTitleNum") int answerTitleNum);

    List<TitleModel<OptionModel>> getTitlePage(@Param("surveyId") String surveyId,@Param("pageSize") int pageSize,@Param("start") int start);

    List<TitleAnswerModel> getTitleAnswerPage(@Param("surveyId") String surveyId,@Param("pageSize") int pageSize,@Param("start") int start);

    List<TitleAnswerModel> getTitleAnswerByIds(List<String> titleIdList);

    List<TitleModel> queryTitleModel(@Param("surveyId") String surveyId, @Param("titleName") String titleName);

    List<TitleModel> queryNameColumnBySurveyId(String surveyId);

    List<SubmitTitleModel> querySubmitTitleList(@Param("titleId") String titleId,@Param("titleType") String titleType);

    int saveTitleModel(TitleModel titleModel);

    int saveTitleModelList(List<TitleModel<OptionModel>> titleModelList);

    int updateTitleModel(TitleModel titleModel);

    int deleteTitleModel(String titleId);

    int deleteTitleModelList(List<TitleModel> titleModelList);

    int updateTitleModelSequence(List<TitleModel> titleModelList);

}
