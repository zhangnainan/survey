package com.sg.survey;

/**
 * Created by jiuge on 2020/5/7.
 */
public enum SurveyState {
    On("1"),Off("0");
    private String state;
    private SurveyState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
