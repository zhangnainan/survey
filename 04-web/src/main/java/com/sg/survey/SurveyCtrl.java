package com.sg.survey;

import com.alibaba.fastjson.JSON;
import com.sg.survey.title.TitleInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Created by jiuge on 2020/5/26.
 */
@Controller("com.sg.survey.ctrl")
@RequestMapping("/survey")
public class SurveyCtrl{


    @Autowired
    private SurveyService surveyService;

    @ResponseBody
    @RequestMapping(value = "/get/survey/list",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveyModelList(String creator){

        Result result = surveyService.getSurveyModelListByCreator(creator);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurvey(String surveyId){

        Result result = surveyService.getSurveyTitleOptionModel(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/load/survey",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String loadSurvey(String surveyId, String wxNickname){

        Result result = surveyService.getSurveyTitleOptionModel(surveyId,wxNickname);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/submit/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String submitSurveyTitleOptionModel(@RequestBody  SurveyTitleOptionModel<TitleInfoModel> survey, String wxNickname){

        Result result = surveyService.submitSurveyTitleOptionModel(survey,wxNickname);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/summary",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveySummary(String surveyId){

        Result result = surveyService.getSurveySummary(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/submit/list",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveySubmitDetail(String surveyId){

        Result result = surveyService.getSurveySubmitDetailList(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/name/statistics",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveyByNameStatistics(String surveyId,String titleId){

        Result result = surveyService.getSurveyByNameStatistics(surveyId,titleId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/save/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String saveSurvey(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.saveSurvey(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/update/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String updateSurvey(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.updateSurvey(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/survey",method = RequestMethod.DELETE,produces = "text/plain;charset=utf-8")
    public String deleteSurvey(@RequestBody Map<String,Object> paramsMap){

        String surveyId = paramsMap.get("surveyId").toString();
        Result result = surveyService.deleteSurvey(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/clear/survey",method = RequestMethod.DELETE,produces = "text/plain;charset=utf-8")
    public String clearSurvey(@RequestBody Map<String,Object> paramsMap){

        String surveyId = paramsMap.get("surveyId").toString();
        Result result = surveyService.clearSurvey(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/update/survey/time/setting",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String updateSurveyTimeSetting(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.updateSurveyTimeSetting(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

}
