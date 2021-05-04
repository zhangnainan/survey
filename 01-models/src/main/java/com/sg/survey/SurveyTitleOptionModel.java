package com.sg.survey;

import java.util.List;

/**
 * Created by jiuge on 2020/5/22.
 */
public class  SurveyTitleOptionModel <T>  {

    protected String id;
    protected String surveyName;
    protected String surveyState;
    protected String surveyType;
    protected String creator;
    protected int answerTitleNum;
    protected String notes;
    protected String beginDateTime;
    protected String endDateTime;
    protected List<T> titleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyState() {
        return surveyState;
    }

    public void setSurveyState(String surveyState) {
        this.surveyState = surveyState;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getAnswerTitleNum() {
        return answerTitleNum;
    }

    public void setAnswerTitleNum(int answerTitleNum) {
        this.answerTitleNum = answerTitleNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<T> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<T> titleList) {
        this.titleList = titleList;
    }
}
