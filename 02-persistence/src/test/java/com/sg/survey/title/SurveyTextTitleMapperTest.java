package com.sg.survey.title;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiuge on 2020/5/17.
 */
public class SurveyTextTitleMapperTest {

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyTextTitleDao surveyTextTitleDao = (SurveyTextTitleDao) applicationContext.getBean("surveyTextTitleDao");
        List<SurveyTextTitleModel> surveyTextTitleModelList = surveyTextTitleDao.querySurveyTextTitleBySurveyId("0");
    }

}
