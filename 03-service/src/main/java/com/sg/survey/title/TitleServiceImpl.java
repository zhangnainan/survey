package com.sg.survey.title;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import com.sg.survey.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by jiuge on 2020/6/26.
 */
@Service("com.sg.survey.title.service.impl")
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleDao titleDao;

    @Override
    public Result getTextTitleModelList(String surveyId) {
        Result result = new Result();
        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            List<TitleModel> titleModelList = titleDao.getTitleModelList(surveyId,TitleType.Text.getVal());
            result.setData(titleModelList);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(Message.Error.getType());
        }

        return result;

    }
}
