package com.sg.survey;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.survey.model")
public class SurveyModel {

    private String id;
    private String surveyName;
    private String surveyState;
    private String creator;
    private String notes;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }
}
