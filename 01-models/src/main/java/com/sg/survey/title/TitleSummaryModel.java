package com.sg.survey.title;

import com.sg.survey.submit.SubmitTitleAnswerModel;
import com.sg.survey.title.option.OptionSummaryModel;

import java.util.List;

/**
 * Created by jiuge on 2020/6/11.
 */
public class TitleSummaryModel extends TitleModel<OptionSummaryModel> {

    private List<SubmitTitleAnswerModel> submitTitleAnswerModelList;

    public List<SubmitTitleAnswerModel> getSubmitTitleAnswerModelList() {
        return submitTitleAnswerModelList;
    }

    public void setSubmitTitleAnswerModelList(List<SubmitTitleAnswerModel> submitTitleAnswerModelList) {
        this.submitTitleAnswerModelList = submitTitleAnswerModelList;
    }
}
