package com.sg.survey.user;

import com.alibaba.fastjson.JSON;
import com.sg.survey.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiuge on 2020/6/23.
 */
@Controller("com.sg.survey.user.ctrl")
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveyModelList(UserModel userModel){

        Result result = userService.login(userModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

}
