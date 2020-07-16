package com.sg.survey.title;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import com.sg.survey.submit.SubmitTitleModel;
import com.sg.survey.title.option.OptionDao;
import com.sg.survey.title.option.OptionModel;
import com.sg.survey.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jiuge on 2020/6/26.
 */
@Service("com.sg.survey.title.service.impl")
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private OptionDao optionDao;


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

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result saveTitleModel(TitleModel<OptionModel> titleModel) {
        Result result = new Result();
        if(Validator.isEmpty(titleModel) || Validator.isEmpty(titleModel.getTitle())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        titleModel.setId(UUID.randomUUID().toString());
        String titleType = titleModel.getTitleType();
        if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal()) ){
            if(Validator.isEmpty(titleModel.getOptionModelList())){
                result.setMessage(Message.NotEmpty.getType());
                return result;
            }

            List<String> optionNameList = titleModel.getOptionModelList().stream().map(OptionModel::getOptionName)
                    .collect(Collectors.toList());
            long count = optionNameList.stream().distinct().count();
            if(optionNameList.size() < count){
                result.setMessage(Message.Repeat.getType());
                return result;
            }
            int optionSequence = 1;
            for(OptionModel optionModel : titleModel.getOptionModelList()){
                optionModel.setId(UUID.randomUUID().toString());
                optionModel.setTitleId(titleModel.getId());
                optionModel.setOptionSequence(optionSequence);
                optionSequence ++;
            }
        }




    //    try{
        // 判断数据库中是否已经存在相同的题目
        List<TitleModel> theSameTitleNameList = titleDao.queryTitleModel(titleModel.getSurveyId(),titleModel.getTitle());
        if(!Validator.isEmpty(theSameTitleNameList)){
            result.setMessage(Message.Exist.getType());
            return result;
        }
        // 设置titleSequence
        List<TitleModel> titleModelList = titleDao.getAllTitleModelList(titleModel.getSurveyId());
        int titleSequence = 0;
        if(Validator.isEmpty(titleModelList) || titleModelList.size() == 0){
            titleSequence = 1;
        }else{
            titleSequence = titleModelList.size()+1;
        }
        titleModel.setTitleSequence(titleSequence);
        // 保存titleModel
        int rows = titleDao.saveTitleModel(titleModel);
        if(rows <= 0){
            result.setMessage(Message.AddError.getType());
            return result;
        }
        // 保存OptionModel
        if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
            rows = optionDao.insertOptionList(titleModel.getOptionModelList());
            if(rows <= 0){
                result.setMessage(Message.AddError.getType());
                return result;
            }
        }
        result.setMessage(Message.Success.getType());
    //    }catch (Exception e){
    //        result.setMessage(Message.Error.getType());
    //    }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result updateTitleModel(TitleModel<OptionModel> titleModel) {
        Result result = new Result();
        if(Validator.isEmpty(titleModel) || Validator.isEmpty(titleModel.getTitle())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        String titleType = titleModel.getTitleType();
        if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal()) ){
            if(Validator.isEmpty(titleModel.getOptionModelList())){
                result.setMessage(Message.NotEmpty.getType());
                return result;
            }

            List<String> optionNameList = titleModel.getOptionModelList().stream().map(OptionModel::getOptionName)
                    .collect(Collectors.toList());
            long count = optionNameList.stream().distinct().count();
            if(optionNameList.size() < count){
                result.setMessage(Message.Repeat.getType());
                return result;
            }
            int optionSequence = 1;
            for(OptionModel optionModel : titleModel.getOptionModelList()){
                optionModel.setOptionSequence(optionSequence);
                optionSequence ++;
            }
        }




        //    try{
        // 判断数据库中是否已经存在相同的题目
        List<TitleModel> theSameTitleNameList = titleDao.queryTitleModel(titleModel.getSurveyId(),titleModel.getTitle());
        if(!Validator.isEmpty(theSameTitleNameList)){
            for(int i = 0; i < theSameTitleNameList.size(); i++) {
                if (!theSameTitleNameList.get(i).getId().equals(titleModel.getId())) {
                    result.setMessage(Message.Exist.getType());
                    return result;
                }
            }
        }

        // 保存titleModel
        int rows = titleDao.updateTitleModel(titleModel);
        if(rows <= 0){
            result.setMessage(Message.AddError.getType());
            return result;
        }
        // 保存OptionModel
        if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
            List<OptionModel> updateList = new ArrayList<>();
            List<OptionModel> insertList = new ArrayList<>();
            for(OptionModel optionModel : titleModel.getOptionModelList()){
                if(Validator.isEmpty(optionModel.getId())){
                    optionModel.setId(UUID.randomUUID().toString());
                    optionModel.setTitleId(titleModel.getId());
                    insertList.add(optionModel);
                }else{
                    updateList.add(optionModel);
                }
            }
            if(!Validator.isEmpty(updateList)){
                rows = optionDao.updateOptionList(updateList);
                if(rows <= 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }
            if(!Validator.isEmpty(insertList)){
                rows =optionDao.insertOptionList(insertList);
                if(rows <= 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }
        }
        result.setData(titleModel);
        result.setMessage(Message.Success.getType());

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result deleteTitleModel(TitleModel<OptionModel> titleModel) {
        Result result = new Result();
        if(Validator.isEmpty(titleModel) || Validator.isEmpty(titleModel.getId())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

    //    try {
        // 查找该题目是否已经有答卷提交
        List<SubmitTitleModel> submitTitleModelList = titleDao.querySubmitTitleList(titleModel.getId(),titleModel.getTitleType());
        if(!Validator.isEmpty(submitTitleModelList)){
            result.setMessage(Message.AnswerExist.getType());
            return result;
        }
        int rows = titleDao.deleteTitleModel(titleModel.getId());
        if(rows < 0){
            result.setMessage(Message.DeleteError.getType());
            return result;
        }

        String titleType = titleModel.getTitleType();
        if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
            if(!Validator.isEmpty(titleModel.getOptionModelList())){
                rows = optionDao.deleteOptionModelList(titleModel.getOptionModelList());
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }
            }
        }
        // 更新titleSequence
        if(!Validator.isEmpty(titleModel.getSurveyId())){
            List<TitleModel> titleModelList = titleDao.getAllTitleModelList(titleModel.getSurveyId());
            if(!Validator.isEmpty(titleModelList)){
                for(int i = 0; i < titleModelList.size(); i++){
                    titleModelList.get(i).setTitleSequence(i+1);
                }
                rows = titleDao.updateTitleModelSequence(titleModelList);
                if(rows < 0){
                    result.setMessage(Message.UpdateError.getType());
                    return result;
                }
            }
        }
        // 还要删除答案
        result.setMessage(Message.Success.getType());
    //    }catch (Exception e){
    //        e.printStackTrace();
    //        result.setMessage(Message.Error.getType());
    //    }

        return result;
    }
}
