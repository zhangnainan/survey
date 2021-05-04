package com.sg.survey.title.answer;

import java.util.List;

public interface AnswerDao {

    public int saveAnswerModelList(List<AnswerModel> answerModelList);

    public int deleteAnswerModelList(List<AnswerModel> answerModelList);

    public int deleteAnswerModelByAnswer(String answer);

    public int deleteAnswerModelByTitleId(String titleId);

    public int updateAnswerModelList(List<AnswerModel> updateAnswerModelList);
}
