package com.sg.survey.title.option;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import com.sg.survey.submit.SubmitOptionModel;
import com.sg.survey.title.TitleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiuge on 2020/7/14.
 */
@Service("com.sg.survey.title.option.service.impl")
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override
    public Result deleteOptionModel(String optionId, String titleType) {
        Result result = new Result();

        if(Validator.isEmpty(optionId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        if(!(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal()))){
            result.setMessage(Message.ParameterIllegal.getType());
            return result;
        }
        List<SubmitOptionModel> submitOptionModelList = optionDao.querySubmitOptionList(optionId,titleType);

        if(!Validator.isEmpty(submitOptionModelList)){
            result.setMessage(Message.AnswerExist.getType());
            return result;
        }
        int rows = optionDao.deleteOptionModel(optionId);
        if(rows < 0){
            result.setMessage(Message.DeleteError.getType());
        }

        result.setMessage(Message.Success.getType());

        return result;
    }
}
