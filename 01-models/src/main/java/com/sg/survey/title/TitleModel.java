package com.sg.survey.title;

import com.sg.survey.title.option.OptionModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/22.
 */
public class TitleModel <T>  implements Comparable<TitleModel>{

    protected String id;
    protected String surveyId;
    protected String title;
    protected String titleType;
    protected String required;
    protected int titleSequence;
    protected List<T> optionModelList;


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

    public List<T> getOptionModelList() {
        return optionModelList;
    }

    public void setOptionModelList(List<T> optionModelList) {
        this.optionModelList = optionModelList;
    }


    @Override
    public int compareTo(TitleModel o) {
        return this.titleSequence - o.titleSequence;
    }
}
