package com.sg.survey.title;

import com.sg.survey.title.option.OptionStatisticsModel;

import java.util.List;

/**
 * Created by jiuge on 2020/6/28.
 */
public class TitleStatisticsModel extends TitleModel<OptionStatisticsModel> {

    private List<TextNameModel> textNameList;

    public List<TextNameModel> getTextNameList() {
        return textNameList;
    }

    public void setTextNameList(List<TextNameModel> textNameList) {
        this.textNameList = textNameList;
    }

}
