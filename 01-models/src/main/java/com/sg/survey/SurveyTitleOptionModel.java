package com.sg.survey;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/22.
 */
public class  SurveyTitleOptionModel <T>  {

    protected String id;
    protected String surveyName;
    protected String surveyState;
    protected String creator;
    protected String notes;

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



    public List<T> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<T> titleList) {
        this.titleList = titleList;
    }
}
