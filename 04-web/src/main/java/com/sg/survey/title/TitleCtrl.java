package com.sg.survey.title;

import com.alibaba.fastjson.JSON;
import com.sg.survey.Result;
import com.sg.survey.title.option.OptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiuge on 2020/6/26.
 */
@Controller("com.sg.survey.title.ctrl")
@RequestMapping("/title")
public class TitleCtrl {

    @Autowired
    private TitleService titleService;

    @ResponseBody
    @RequestMapping(value = "/text/get",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getTextTitleModelList(String surveyId){

        Result result = titleService.getTextTitleModelList(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String saveTitleModel(@RequestBody TitleModel<OptionModel> titleModel){

        Result result = titleService.saveTitleModel(titleModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String updateTitleModel(@RequestBody TitleModel<OptionModel> titleModel){
        Result result = titleService.updateTitleModel(titleModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "text/plain;charset=utf-8")
    public String deleteTitleModel(@RequestBody TitleModel<OptionModel> titleModel){
        Result result = titleService.deleteTitleModel(titleModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

}
