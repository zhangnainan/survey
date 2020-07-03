package com.sg.survey.submit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.submit.submit.single.title.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubmitSingleTitleModel extends SubmitTitleModel {

    private String optionId;

    public SubmitSingleTitleModel(){

    }

    public SubmitSingleTitleModel(String id,String submitId,String titleId,String optionId){
        this.id = id;
        this.submitId = submitId;
        this.titleId = titleId;
        this.optionId = optionId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}
