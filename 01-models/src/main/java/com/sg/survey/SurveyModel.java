package com.sg.survey;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.survey.model")
public class SurveyModel {

    private String id;
    private String surveyName;
    private String surveyState;
    private String surveyType;
    private String creator;
    private int answerTitleNum;
    private String notes;
    private String beginDateTime;
    private String endDateTime;
    private int submitCount;

    public SurveyModel(){

    }

    public SurveyModel(String id,String surveyName,String surveyState,String creator,String notes){
        this.id = id;
        this.surveyName = surveyName;
        this.surveyState =surveyState;
        this.creator = creator;
        this.notes = notes;
    }

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

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }
}
