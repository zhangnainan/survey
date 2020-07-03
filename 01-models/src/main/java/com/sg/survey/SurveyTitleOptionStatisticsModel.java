package com.sg.survey;

import com.sg.survey.SurveyTitleOptionModel;
import com.sg.survey.title.TitleStatisticsModel;

/**
 * Created by jiuge on 2020/6/29.
 */
public class SurveyTitleOptionStatisticsModel extends SurveyTitleOptionModel<TitleStatisticsModel> {

    private int submitCount;

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }
}
