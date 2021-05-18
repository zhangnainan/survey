package com.sg.survey.title;

import com.sg.survey.Result;
import com.sg.survey.title.answer.AnswerModel;
import com.sg.survey.title.option.OptionModel;

import java.util.List;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleService {

    Result getTextTitleModelList(String surveyId);

    Result getTitleCount(String surveyId);

    Result getAllTitle(String surveyId);

    Result getContestTitle(String surveyId, int answerTitleNum);

    Result getTitlePage(String surveyId, String surveyType, int pageSize, int start);

    Result saveTitleModel(TitleModel<OptionModel> titleModel);

    Result saveTitleAndAnswerModelList(List<TitleModel<OptionModel>> titleModelList, List<AnswerModel> answerModelList);

    Result updateTitleModel(TitleModel<OptionModel> titleModel);

    Result deleteTitleModel(TitleModel<OptionModel> titleModel);

    Result getStartedTitleSequence(String surveyId);


}
