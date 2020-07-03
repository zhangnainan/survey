package com.sg.survey.title;

import com.sg.survey.title.option.OptionModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
@Entity
@Component("survey.title.survey.single.title.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SurveySingleTitleModel extends SurveyTitleModel {

    private List<OptionModel> titleOptionList;

    public List<OptionModel> getTitleOptionList() {
        return titleOptionList;
    }

    public void setTitleOptionList(List<OptionModel> titleOptionList) {
        this.titleOptionList = titleOptionList;
    }
}
