package com.sg.survey.submit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.submit.survey.submit.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SurveySubmitModel {

    private String id;
    private String  surveyId;
    private String submitter;
    private String wxNickname;
    private String wxOpenId;

    public SurveySubmitModel(){

    }

    public SurveySubmitModel(String id,String surveyId,String submitter,String wxNickname,String wxOpenId){
        this.id = id;
        this.surveyId   = surveyId;
        this.submitter  = submitter;
        this.wxNickname = wxNickname;
        this.wxOpenId   = wxOpenId;
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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getWxNickname() {
        return wxNickname;
    }

    public void setWxNickname(String wxNickname) {
        this.wxNickname = wxNickname;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
}
