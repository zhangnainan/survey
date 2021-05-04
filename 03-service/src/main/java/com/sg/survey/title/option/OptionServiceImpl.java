package com.sg.survey.title.option;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import com.sg.survey.submit.SubmitOptionModel;
import com.sg.survey.title.TitleType;
import com.sg.survey.title.answer.AnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiuge on 2020/7/14.
 */
@Service("com.sg.survey.title.option.service.impl")
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private AnswerDao answerDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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
        if(rows < 0) {
            result.setMessage(Message.DeleteError.getType());
        }
        // 知识竞赛OptionModel有可能是answer，所以删除OptionModel的时候需要删除answer。
        rows = answerDao.deleteAnswerModelByAnswer(optionId);
        if(rows < 0){
            result.setMessage(Message.DeleteError.getType());
        }
        result.setMessage(Message.Success.getType());

        return result;
    }
}
