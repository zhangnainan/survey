package com.sg.survey.title;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.title.survey.title.model")
public class SurveyTitleModel {

    protected String id;
    protected String surveyId;
    protected String title;
    protected String titleType;
    protected String required;
    protected int titleSequence;

    public SurveyTitleModel(){

    }

    public SurveyTitleModel(String id,String surveyId, String title, String titleType, String required, int sequence){
        this.id = id;
        this.surveyId = surveyId;
        this.title = title;
        this.titleType = titleType;
        this.required = required;
        this.titleSequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public int getTitleSequence() {
        return titleSequence;
    }

    public void setTitleSequence(int titleSequence) {
        this.titleSequence = titleSequence;
    }
}
