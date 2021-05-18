package com.sg.survey.title.answer;

import com.sg.survey.title.TitleModel;

import java.util.List;

public interface AnswerDao {

    int saveAnswerModelList(List<AnswerModel> answerModelList);

    int deleteAnswerModelList(List<AnswerModel> answerModelList);

    int deleteAnswerModelByAnswer(String answer);

    int deleteAnswerByTitleIds(List<TitleModel> titleModelList);

    int deleteAnswerModelByTitleId(String titleId);

    int updateAnswerModelList(List<AnswerModel> updateAnswerModelList);
}
