package com.sg.survey.submit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import javax.persistence.*;

/**
 * Created by jiuge on 2020/5/7.
 */
@Entity
@Component("survey.submit.submit.multiple.title.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubmitMultipleTitleModel extends SubmitTitleModel {


    private List<String> optionIdList;

    public SubmitMultipleTitleModel(){

    }

    public SubmitMultipleTitleModel(String id,String submitId,String titleId,List<String> optionIdList){
        this.id = id;
        this.submitId = submitId;
        this.titleId = titleId;
        this.optionIdList = optionIdList;
    }


    public List<String> getOptionIdList() {
        return optionIdList;
    }

    public void setOptionIdList(List<String> optionIdList) {
        this.optionIdList = optionIdList;
    }
}
