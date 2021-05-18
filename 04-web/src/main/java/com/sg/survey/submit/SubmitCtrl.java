package com.sg.survey.submit;


import com.alibaba.fastjson.JSON;
import com.sg.survey.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("com.sg.survey.submit.ctrl")
@RequestMapping("/submit")
public class SubmitCtrl {

    @Autowired
    private SubmitService submitService;

    @ResponseBody
    @RequestMapping(value = "/survey/get",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveySubmitList(String surveyId, String wxOpenId){

        Result result = submitService.getSurveySubmitList(surveyId,wxOpenId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/contest/rank/get",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getContestRankPage(String surveyId, int pageSize, int start){

        Result result = submitService.getContestRankPage(surveyId, pageSize, start);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/count/get",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSubmitCount(String surveyId){

        Result result = submitService.getSubmitCount(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }
}
