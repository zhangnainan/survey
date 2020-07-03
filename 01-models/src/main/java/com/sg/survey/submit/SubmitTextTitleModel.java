package com.sg.survey.submit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.submit.submit.text.title.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubmitTextTitleModel extends SubmitTitleModel {
    private String text;

    public SubmitTextTitleModel(){

    }

    public SubmitTextTitleModel(String id,String submitId,String titleId,String text){
        this.id = id;
        this.submitId = submitId;
        this.titleId = titleId;
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
