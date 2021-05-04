package com.sg.survey.title.option;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.title.option.option.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OptionModel implements Comparable<OptionModel>{

    protected String id;
    protected String titleId;
    protected String optionName;
    protected int optionSequence;
    protected boolean selected;
    protected boolean corrected;


    public OptionModel(){

    }

    public OptionModel(String id, String titleId, String optionName, int sequence){
        this.id = id;
        this.titleId = titleId;
        this.optionName = optionName;
        this.optionSequence = sequence;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public  String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getOptionSequence() {
        return optionSequence;
    }

    public void setOptionSequence(int optionSequence) {
        this.optionSequence = optionSequence;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCorrected() {
        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }

    @Override
    public int compareTo(OptionModel o) {
        return this.getOptionSequence()-o.getOptionSequence();
    }
}
