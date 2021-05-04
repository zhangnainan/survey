package com.sg.survey.title;

import com.sg.survey.Result;
import com.sg.survey.title.answer.AnswerModel;
import com.sg.survey.title.option.OptionModel;

import java.util.List;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleService {

    public Result getTextTitleModelList(String surveyId);

    public Result getTitleCount(String surveyId);

    public Result getAllTitle(String surveyId);

    public Result getTitlePage(String surveyId, String surveyType, int pageSize, int start);

    public Result saveTitleModel(TitleModel<OptionModel> titleModel);

    public Result saveTitleAndAnswerModelList(List<TitleModel<OptionModel>> titleModelList, List<AnswerModel> answerModelList);

    public Result updateTitleModel(TitleModel<OptionModel> titleModel);

    public Result deleteTitleModel(TitleModel<OptionModel> titleModel);

    public Result getStartedTitleSequence(String surveyId);
}
