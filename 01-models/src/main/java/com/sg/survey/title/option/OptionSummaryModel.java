package com.sg.survey.title.option;

/**
 * Created by jiuge on 2020/6/11.
 */
public class OptionSummaryModel extends OptionModel{

    private int submitCount;
    private String percent;

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
