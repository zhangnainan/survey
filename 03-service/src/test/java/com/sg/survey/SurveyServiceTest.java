package com.sg.survey;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiuge on 2020/5/27.
 */
public class SurveyServiceTest {
    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-service.xml");
        SurveyService surveyService = (SurveyService) applicationContext.getBean("survey.survey.service.impl");
        Result result = surveyService.getSurveyTitleOptionModel("636b0ff1-1fe9-440e-bcf2-dbd2b0e3237c");
    }
}
