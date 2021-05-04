package com.sg.survey.title;

import com.sg.survey.title.answer.AnswerModel;
import com.sg.survey.title.option.OptionModel;

import java.util.List;

public class TitleAnswerModel extends TitleModel<OptionModel> {

    private List<AnswerModel> answerModelList;

    public List<AnswerModel> getAnswerModelList() {
        return answerModelList;
    }

    public void setAnswerModelList(List<AnswerModel> answerModelList) {
        this.answerModelList = answerModelList;
    }
}
