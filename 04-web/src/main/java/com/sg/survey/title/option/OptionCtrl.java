package com.sg.survey.title.option;

import com.alibaba.fastjson.JSON;
import com.sg.survey.Result;
import com.sg.survey.title.TitleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by jiuge on 2020/7/14.
 */
@Controller("com.sg.survey.title.option.ctrl")
@RequestMapping("/option")
public class OptionCtrl {

    @Autowired
    private OptionService optionService;

    @ResponseBody
    @DeleteMapping(value = "/delete",  produces = "text/plain;charset=utf-8")
    public String deleteTitleModel(@RequestBody Map<String,Object> paramsMap){

        String optionId = paramsMap.get("optionId").toString();
        String titleType = paramsMap.get("titleType").toString();
        Result result = optionService.deleteOptionModel(optionId,titleType);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }
}
