package com.sg.survey;

import com.sg.survey.title.option.OptionDao;
import com.sg.survey.title.option.OptionModel;
import com.sg.survey.submit.*;
import com.sg.survey.title.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jiuge on 2020/5/7.
 */
    public class SurveyDaoTest {
    public static void main(String[] args){
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyDao surveyDao = (SurveyDao) applicationContext.getBean("surveyDao");
        int deleteRows = surveyDao.deleteSurvey("1");
        List<SurveyModel> surveyModels = surveyDao.queryAll();*/
        // 创建问卷
        //createSurvey();
        // 查询问卷信息
        //querySurveyTitleOptionModelTest();
        //提交问卷
        //submitSurvey();
        //查询汇总结果
        //getSubmitSummaryResult();
        // 获取答卷详情
        //getSurveySubmitDetailList();
        //md5Test();
        //获取题目列表
        //getTitleModelList();
        //getStatistics
        //getSurveyStatistics();
        //根据调查名称查询问卷
        //querySurveyByName();
        querySubmitTitleList();
    }

    private static void md5Test(){
        String md5Str = DigestUtils.md5DigestAsHex("sgzj@1958".getBytes());
        System.out.println(md5Str);
        //System.out.println(UUID.randomUUID().toString());
    }

    private static void getTitleModelList(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        TitleDao titleDao = (TitleDao) applicationContext.getBean("titleDaoMapper");
        List<TitleModel> titleModelList = titleDao.getTitleModelList("3cf1e381-d59c-42c6-b477-80dea82f01d5","");
    }

    private static void querySurveyTitleOptionModelTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyTitleOptionDao surveyTitleOptionDao = (SurveyTitleOptionDao) applicationContext.getBean("surveyTitleOptionDaoMapper");

        SurveyTitleOptionModel surveyTitleOptionModel = surveyTitleOptionDao.getSurveyTitleOptionModelById("7503badc-f574-48e5-a167-732450f00b5f");
    }

    private static void getSurveyStatistics(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyTitleOptionDao surveyTitleOptionDao = (SurveyTitleOptionDao) applicationContext.getBean("surveyTitleOptionDaoMapper");

        SurveyTitleOptionModel surveyTitleOptionModel = surveyTitleOptionDao.getSurveyStatistics("3cf1e381-d59c-42c6-b477-80dea82f01d5");
    }

    private static void getSubmitSummaryResult(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyTitleOptionDao surveyTitleOptionDao = (SurveyTitleOptionDao) applicationContext.getBean("surveyTitleOptionDaoMapper");
        SurveyTitleOptionSummaryModel surveyTitleOptionSummaryModel = surveyTitleOptionDao.getSurveySubmitSummary("3cf1e381-d59c-42c6-b477-80dea82f01d5");
    //    List<SubmitSummaryModel> submitSummaryModelList = surveyTitleOptionDao.getSurveySubmitSummary("636b0ff1-1fe9-440e-bcf2-dbd2b0e3237c");
    }

    private static void getSurveySubmitDetailList(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyTitleOptionDao surveyTitleOptionDao = (SurveyTitleOptionDao) applicationContext.getBean("surveyTitleOptionDaoMapper");
        List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList = surveyTitleOptionDao.getSurveySubmitDetailList("3cf1e381-d59c-42c6-b477-80dea82f01d5");
    }

    private static void querySurveyByName(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyDao surveyDao = (SurveyDao) applicationContext.getBean("surveyDaoMapper");
        List<SurveyModel> surveyModelList = surveyDao.queryByName("ssss");
    }

    private static void querySubmitTitleList(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        TitleDao titleDao = (TitleDao) applicationContext.getBean("titleDaoMapper");
        List<SubmitTitleModel> submitTitleModelList = titleDao.querySubmitTitleList("54a8d096-ea33-424b-a84a-efde735cb6a0","0");
    }
    private static void createSurvey(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        SurveyDao surveyDao = (SurveyDao) applicationContext.getBean("surveyDaoMapper");
        SurveyTitleDao surveyTitleDao = (SurveyTitleDao)applicationContext.getBean("surveyTitleDaoMapper");
        OptionDao optionDao = (OptionDao) applicationContext.getBean("optionDaoMapper");

        List<SurveyTitleModel> surveyTitleModelList = new ArrayList<>();
        List<OptionModel> optionModelList = new ArrayList<>();

        SurveyModel surveyModel = new SurveyModel();
        surveyModel.setId(UUID.randomUUID().toString());
        surveyModel.setSurveyName("2020年质计部工会调查表1");
        surveyModel.setSurveyState(SurveyState.On.getState());
        surveyModel.setNotes("");
        surveyModel.setCreator("张大帅");

        SurveyTitleModel surveyTitleModel1 = new SurveyTitleModel(UUID.randomUUID().toString(),surveyModel.getId(),"科站", TitleType.SingleTitle.getVal(), RequiredFlag.True.getFlag(),1);
        OptionModel optionModel1 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel1.getId(),"冶炼站",1);
        OptionModel optionModel2 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel1.getId(),"中检所",2);
        OptionModel optionModel3 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel1.getId(),"原料站",3);
        OptionModel optionModel4 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel1.getId(),"计量科",4);
        surveyTitleModelList.add(surveyTitleModel1);
        optionModelList.add(optionModel1);
        optionModelList.add(optionModel2);
        optionModelList.add(optionModel3);
        optionModelList.add(optionModel4);


        SurveyTitleModel surveyTitleModel2 = new SurveyTitleModel(UUID.randomUUID().toString(),surveyModel.getId(),"岗位", TitleType.SingleTitle.getVal(), RequiredFlag.True.getFlag(),2);
        OptionModel optionModel21 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel2.getId(),"废钢判定",1);
        OptionModel optionModel22 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel2.getId(),"二化",2);
        OptionModel optionModel23 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel2.getId(),"管理",3);
        OptionModel optionModel24 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel2.getId(),"仪表",4);
        surveyTitleModelList.add(surveyTitleModel2);
        optionModelList.add(optionModel21);
        optionModelList.add(optionModel22);
        optionModelList.add(optionModel23);
        optionModelList.add(optionModel24);




        SurveyTitleModel surveyTitleModel3 = new SurveyTitleModel(UUID.randomUUID().toString(),surveyModel.getId(),"姓名", TitleType.Text.getVal(), RequiredFlag.True.getFlag(),3);
        surveyTitleModelList.add(surveyTitleModel3);

        SurveyTitleModel surveyTitleModel4 = new SurveyTitleModel(UUID.randomUUID().toString(),surveyModel.getId(),"爱好", TitleType.MultipleTitle.getVal(), RequiredFlag.True.getFlag(),4);
        OptionModel optionModel41 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel4.getId(),"篮球",1);
        OptionModel optionModel42 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel4.getId(),"足球",2);
        OptionModel optionModel43 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel4.getId(),"排球",3);
        OptionModel optionModel44 = new OptionModel(UUID.randomUUID().toString(),surveyTitleModel4.getId(),"跑步",4);
        surveyTitleModelList.add(surveyTitleModel4);
        optionModelList.add(optionModel41);
        optionModelList.add(optionModel42);
        optionModelList.add(optionModel43);
        optionModelList.add(optionModel44);


        surveyDao.saveSurvey(surveyModel);
        surveyTitleDao.insertSurveyTitleList(surveyTitleModelList);
        optionDao.insertOptionList(optionModelList);
    }


    private static void submitSurvey(){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
//        SurveyTitleOptionDao surveyTitleOptionDao = (SurveyTitleOptionDao) applicationContext.getBean("surveyTitleOptionDaoMapper");
//        SurveyTitleOptionModel surveyTitleOptionModel = surveyTitleOptionDao.getSurveyTitleOptionModelById("636b0ff1-1fe9-440e-bcf2-dbd2b0e3237c");
//
//        SurveySubmitDao surveySubmitDao = (SurveySubmitDao)applicationContext.getBean("surveySubmitDaoMapper");
//        SubmitTextTitleDao submitTextTitleDao = (SubmitTextTitleDao) applicationContext.getBean("submitTextTitleDaoMapper");
//        SubmitSingleTitleDao submitSingleTitleDao = (SubmitSingleTitleDao) applicationContext.getBean("submitSingleTitleDaoMapper");
//        SubmitMultipleTitleDao submitMultipleTitleDao = (SubmitMultipleTitleDao) applicationContext.getBean("submitMultipleTitleDaoMapper");
//
//        List<SurveySubmitModel> surveySubmitModelList = new ArrayList<>();
//        List<SubmitTextTitleModel> submitTextTitleModelList = new ArrayList<>();
//        List<SubmitOptionModel> submitSingleOptionModelList = new ArrayList<>();
//        List<SubmitOptionModel> submitMultipleOptionModelList = new ArrayList<>();
//
//        for(int i = 0; i < 3; i++){
//            SurveySubmitModel surveySubmitModel = new SurveySubmitModel(UUID.randomUUID().toString(),surveyTitleOptionModel.getId(),"znn"+i);
//            surveySubmitModelList.add(surveySubmitModel);
//            List<TitleModel> titleList = surveyTitleOptionModel.getTitleList();
//            for(int j = 0; j < titleList.size(); j++){
//                TitleModel titleModel = titleList.get(j);
//                if(titleModel.getTitleType().equals(TitleType.Text.getVal())){
//                    SubmitTextTitleModel submitTextTitleModel = new SubmitTextTitleModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleModel.getId(),"text"+i);
//                    submitTextTitleModelList.add(submitTextTitleModel);
//                }else if (titleModel.getTitleType().equals(TitleType.SingleTitle.getVal())){
//                    Random random = new Random();
//                    int  optionIndex = random.nextInt(100)%titleModel.getOptionModelList().size();
//                    String optionId = titleModel.getOptionModelList().get(optionIndex).getId();
//                    SubmitOptionModel submitOptionModel = new SubmitOptionModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleModel.getId(),optionId);
//                    submitSingleOptionModelList.add(submitOptionModel);
//                }else if(titleModel.getTitleType().equals(TitleType.MultipleTitle.getVal())){
//                    Random random = new Random();
//
//                    for (int k = 0; k < 2; k++){
//                        int optionIndex = random.nextInt(100)%titleModel.getOptionModelList().size();
//                        String optionId = titleModel.getOptionModelList().get(optionIndex).getId();
//                        SubmitOptionModel submitOptionModel = new SubmitOptionModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleModel.getId(),optionId);
//                        submitMultipleOptionModelList.add(submitOptionModel);
//                    }
//
//                }
//            }
//        }
//
//        surveySubmitDao.insertSurveySubmitList(surveySubmitModelList);
//        submitTextTitleDao.insertSubmitTextTitleList(submitTextTitleModelList);
//        submitSingleTitleDao.insertSubmitSingleTitleList(submitSingleOptionModelList);
//        submitMultipleTitleDao.insertSubmitMultipleTitleList(submitMultipleOptionModelList);
    }
}
