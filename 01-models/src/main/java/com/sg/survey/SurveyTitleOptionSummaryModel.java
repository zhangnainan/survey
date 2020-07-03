package com.sg.survey;

import com.sg.survey.submit.SubmitSummaryModel;
import com.sg.survey.title.TitleSummaryModel;

import java.util.List;

/**
 * Created by jiuge on 2020/6/12.
 */
public class SurveyTitleOptionSummaryModel extends SurveyTitleOptionModel<TitleSummaryModel> {
//    private int submitsCount;
//
//    public int getSubmitsCount() {
//        return submitsCount;
//    }
//
//    public void setSubmitsCount(int submitsCount) {
//        this.submitsCount = submitsCount;
//    }

    private List<SubmitSummaryModel> submitSummaryModelList;

    public List<SubmitSummaryModel> getSubmitSummaryModelList() {
        return submitSummaryModelList;
    }

    public void setSubmitSummaryModelList(List<SubmitSummaryModel> submitSummaryModelList) {
        this.submitSummaryModelList = submitSummaryModelList;
    }
}
