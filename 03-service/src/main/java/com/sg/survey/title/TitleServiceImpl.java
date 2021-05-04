package com.sg.survey.title;

import com.sg.survey.*;
import com.sg.survey.submit.SubmitTitleModel;
import com.sg.survey.title.answer.AnswerDao;
import com.sg.survey.title.answer.AnswerModel;
import com.sg.survey.title.option.OptionDao;
import com.sg.survey.title.option.OptionModel;
import org.apache.poi.ss.formula.functions.Na;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiuge on 2020/6/26.
 */
@Service("com.sg.survey.title.service.impl")
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private AnswerDao answerDao;


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
    public Result getTitleCount(String surveyId) {
        Result result = new Result();

        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            int count = titleDao.getTitleCount(surveyId);
            result.setData(count);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    public Result getAllTitle(String surveyId) {
        Result result = new Result();
        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            List<TitleModel> titleModelList = titleDao.getTitleModelList(surveyId,null);
            result.setData(titleModelList);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    public Result getTitlePage(String surveyId, String surveyType, int pageSize, int start) {

        Result result = new Result();
        result.setMessage(Message.Error.getType());

        if(Validator.isEmpty(surveyId) || Validator.isEmpty(surveyType)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        if(pageSize <= 0 || start < 0){
            result.setMessage(Message.ParameterIllegal.getType());
            return result;
        }

        if (surveyType.equals(SurveyType.Questionnaire.getType())){
            List<TitleModel<OptionModel>> titleModelList = titleDao.getTitlePage(surveyId, pageSize, start);
            result.setData(titleModelList);
        }

        if(surveyType.equals(SurveyType.KnowledgeCompetition.getType())){
            List<TitleAnswerModel> titleAnswerModelList = titleDao.getTitleAnswerPage(surveyId, pageSize, start);
            setOptionCorrected(titleAnswerModelList);
            result.setData(titleAnswerModelList);
        }

        result.setMessage(Message.Success.getType());

        return result;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result saveTitleModel(TitleModel<OptionModel> titleModel) {

        Result result = new Result();
        result.setMessage(Message.Error.getType());

        if(Validator.isEmpty(titleModel) || Validator.isEmpty(titleModel.getTitle())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        titleModel.setId(UUID.randomUUID().toString());
        //String titleType = titleModel.getTitleType();
        if(!updateOptionSequence(titleModel,result,SaveType.Create)){
            return result;
        }

        if(titleModel.getIsNameColumn() == null){
            titleModel.setIsNameColumn(NameColumn.NO.getVal());
        }
        if(titleModel.textType() && titleModel.nameSetting()){
            List<TitleModel> nameColumnTitleList = titleDao.queryNameColumnBySurveyId(titleModel.getSurveyId());
            if(!Validator.isEmpty(nameColumnTitleList)){
                result.setMessage(Message.NameColumnExist.getType());
                return result;
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

        Result<Integer> getTitleStartedSequenceResult = getStartedTitleSequence(titleModel.getSurveyId());
        int titleSequence = 0;
        if(getTitleStartedSequenceResult.getMessage().equals(Message.Success.getType())){
            titleSequence = getTitleStartedSequenceResult.getData();
        }else{
            result.setMessage(getTitleStartedSequenceResult.getMessage());
            return result;
        }

        titleModel.setTitleSequence(titleSequence);
        // 保存titleModel
        int rows = titleDao.saveTitleModel(titleModel);
        if(rows <= 0){
            result.setMessage(Message.AddError.getType());
            return result;
        }
        // 保存OptionModel
        if(titleModel.singleType() || titleModel.multipleType()){
            if(isOptionNameRepeat(titleModel)){
                result.setMessage(Message.RepeatOption.getType());
                return result;
            }
            List<AnswerModel> answerModelList = new ArrayList<>();
            List<OptionModel> optionModelList = titleModel.getOptionModelList();
            int answerSequence = 1;
            for(OptionModel optionModel : optionModelList){
                if(optionModel.isCorrected()){
                    AnswerModel answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),optionModel.getId(),answerSequence);
                    answerModelList.add(answerModel);
                    answerSequence++;
                }
            }
            if(!Validator.isEmpty(answerModelList)){
                rows = answerDao.saveAnswerModelList(answerModelList);
                if(rows < 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }

            if(!Validator.isEmpty(titleModel.getOptionModelList())){
                rows = optionDao.insertOptionList(titleModel.getOptionModelList());
                if(rows < 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }
        }
        if(titleModel.textType() && titleModel instanceof  TitleAnswerModel){
            int answerSequence = 1;
            List<AnswerModel> answerModelList = ((TitleAnswerModel) titleModel).getAnswerModelList();
            for(int i = 0; i < answerModelList.size(); i++){
                answerModelList.get(i).setId(UUID.randomUUID().toString());
                answerModelList.get(i).setTitleId(titleModel.getId());
                answerModelList.get(i).setAnswerSequence(answerSequence);
                answerSequence ++;
            }

            rows = answerDao.saveAnswerModelList(answerModelList);
            if(rows < 0){
                result.setMessage(Message.AddError.getType());
                return result;
            }
        }
        result.setMessage(Message.Success.getType());

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result saveTitleAndAnswerModelList(List<TitleModel<OptionModel>> titleModelList, List<AnswerModel> answerModelList) {
        Result result = new Result();
        result.setMessage(Message.UpdateError.getType());

        if(Validator.isEmpty(titleModelList) || Validator.isEmpty(answerModelList)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        List<OptionModel> optionModelList = new ArrayList<>();
        //检查题目是否重复
        List<TitleModel>  existedTitleModelList = titleDao.getAllTitleModelList(titleModelList.get(0).getSurveyId());
        boolean isTitleNameRepeat = isTitleNameRepeat(titleModelList,existedTitleModelList);
        if(isTitleNameRepeat){
            result.setMessage(Message.RepeatTitle.getType());
            return result;
        }
        // 合并所有的OptionModel到一个List，并且检查每一个TitleModel下的OptionModel是否名称有重复
        for(TitleModel<OptionModel> titleModel : titleModelList){
            if(!Validator.isEmpty(titleModel.getOptionModelList())){
                optionModelList.addAll(titleModel.getOptionModelList());
            }
            if(titleModel.multipleType() || titleModel.singleType()){
                if(isOptionNameRepeat(titleModel)){
                    result.setMessage(Message.RepeatOption.getType());
                    return result;
                }
            }
        }
        // 存入数据库
        int rows = -1;
        if(!Validator.isEmpty(titleModelList)){
            rows = titleDao.saveTitleModelList(titleModelList);
            if(rows < 0){
                result.setMessage(Message.AddError.getType());
                return result;
            }
        }

        if(!Validator.isEmpty(optionModelList)){
            rows = optionDao.insertOptionList(optionModelList);
            if(rows < 0){
                result.setMessage(Message.AddError.getType());
                return result;
            }
        }

        if(!Validator.isEmpty(answerModelList)){
            rows = answerDao.saveAnswerModelList(answerModelList);
            if(rows <= 0){
                result.setMessage(Message.AddError.getType());
                return result;
            }
        }

        result.setMessage(Message.Success.getType());

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

        if(!updateOptionSequence(titleModel,result,SaveType.Update)){
            return result;
        }

        if(titleModel.getIsNameColumn() == null){
            titleModel.setIsNameColumn(NameColumn.NO.getVal());
        }

        if(titleModel.textType() && titleModel.nameSetting()){
            List<TitleModel> nameColumnTitleList = titleDao.queryNameColumnBySurveyId(titleModel.getSurveyId());

            if(isTitleSettingDuplicate(nameColumnTitleList, titleModel)){
                result.setMessage(Message.NameColumnExist.getType());
                return result;
            }
        }

        //    try{
        // 判断数据库中是否已经存在相同的题目
        List<TitleModel> theSameTitleNameList = titleDao.queryTitleModel(titleModel.getSurveyId(),titleModel.getTitle());
        if(isTitleSettingDuplicate(theSameTitleNameList, titleModel)){
            result.setMessage(Message.Exist.getType());
            return result;
        }

        // 保存titleModel
        int rows = titleDao.updateTitleModel(titleModel);
        if(rows <= 0){
            result.setMessage(Message.AddError.getType());
            return result;
        }
        // 保存OptionModel及更新AnswerModel
        List<AnswerModel> answerModelList = null;
        if(titleModel instanceof  TitleAnswerModel){
            answerModelList = ((TitleAnswerModel) titleModel).getAnswerModelList();
        }
        if(titleModel.singleType() || titleModel.multipleType()){
            List<OptionModel> updateList = new ArrayList<>();
            List<OptionModel> insertList = new ArrayList<>();
            List<AnswerModel> deleteAnswerModelList = new ArrayList<>();
            List<AnswerModel> updateAnswerModelList = new ArrayList<>();
            List<AnswerModel> insertAnswerModelList = new ArrayList<>();
            int answerSequence = 1;
            for(OptionModel optionModel : titleModel.getOptionModelList()){
                if(Validator.isEmpty(optionModel.getId())){
                    optionModel.setId(UUID.randomUUID().toString());
                    optionModel.setTitleId(titleModel.getId());
                    insertList.add(optionModel);
                    if(optionModel.isCorrected()){
                        AnswerModel answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),optionModel.getId(),answerSequence);
                        answerSequence++;
                        insertAnswerModelList.add(answerModel);
                    }
                }else{
                    if(!Validator.isEmpty(answerModelList) && optionModel.isCorrected()){
                        boolean isAnswerBefore = false;
                        AnswerModel updateAnswerModel = null;
                        for(AnswerModel answerModel : answerModelList){
                            if(answerModel.getAnswer().equals(optionModel.getId())){
                                isAnswerBefore = true;
                                updateAnswerModel = answerModel;
                            }
                        }
                        if(isAnswerBefore){ // 如果之前是答案，更新
                            updateAnswerModel.setAnswerSequence(answerSequence);
                            answerSequence++;
                            updateAnswerModelList.add(updateAnswerModel);
                        }else{ // 如果之前不是答案，新增
                            AnswerModel answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),optionModel.getId(),answerSequence);
                            answerSequence++;
                            insertAnswerModelList.add(answerModel);
                        }
                    }

                    if(Validator.isEmpty(answerModelList) && optionModel.isCorrected()){
                        AnswerModel answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),optionModel.getId(),answerSequence);
                        answerSequence++;
                        insertAnswerModelList.add(answerModel);
                    }

                    if(!Validator.isEmpty(answerModelList) && !optionModel.isCorrected()){
                        for(AnswerModel answerModel : answerModelList){
                            if(answerModel.getAnswer().equals(optionModel.getId())){
                                deleteAnswerModelList.add(answerModel);
                            }
                        }
                    }
                    updateList.add(optionModel);
                }
            }

            if(!Validator.isEmpty(updateList)){
                rows = optionDao.updateOptionList(updateList);
                if(rows < 0){
                    result.setMessage(Message.UpdateError.getType());
                    return result;
                }
            }
            if(!Validator.isEmpty(insertList)){
                rows =optionDao.insertOptionList(insertList);
                if(rows < 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }
            // 更新answerModelList
            if(!Validator.isEmpty(insertAnswerModelList)){
                rows = answerDao.saveAnswerModelList(insertAnswerModelList);
                if(rows < 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }
            }
            if(!Validator.isEmpty(updateAnswerModelList)){
                rows = answerDao.updateAnswerModelList(updateAnswerModelList);
                if(rows < 0){
                    result.setMessage(Message.UpdateError.getType());
                    return result;
                }
            }
            if(!Validator.isEmpty(deleteAnswerModelList)){
                rows = answerDao.deleteAnswerModelList(deleteAnswerModelList);
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }
            }
        }

        if(titleModel.textType() && !Validator.isEmpty(answerModelList)){
            int answerSequence = 1;
            List<AnswerModel> updateList = new ArrayList<>();
            List<AnswerModel> insertList = new ArrayList<>();
            for(AnswerModel answerModel : answerModelList){
                if(Validator.isEmpty(answerModel.getId())){
                    answerModel.setId(UUID.randomUUID().toString());
                    answerModel.setTitleId(titleModel.getId());
                    answerModel.setAnswerSequence(answerSequence);
                    answerSequence ++;
                    insertList.add(answerModel);
                }else{
                    answerModel.setAnswerSequence(answerSequence);
                    updateList.add(answerModel);
                    answerSequence++;
                }
            }
            if(!Validator.isEmpty(insertList)){
                rows = answerDao.saveAnswerModelList(insertList);
                if(rows < 0){
                    result.setMessage(Message.AddError.getType());
                    return result;
                }

            }
            if(!Validator.isEmpty(updateList)){
                rows = answerDao.updateAnswerModelList(updateList);
                if(rows < 0){
                    result.setMessage(Message.UpdateError.getType());
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

        if(titleModel.singleType() || titleModel.multipleType()){
            if(!Validator.isEmpty(titleModel.getOptionModelList())){
                rows = optionDao.deleteOptionModelList(titleModel.getOptionModelList());
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }

                rows = answerDao.deleteAnswerModelByTitleId(titleModel.getId());
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
        // 还要删除答案(因为前面已经限制了如果有人提交过答卷就不允许删除，所以就不用删除答案)
        result.setMessage(Message.Success.getType());


        return result;
    }

    @Override
    public Result getStartedTitleSequence(String surveyId) {
        Result<Integer> result = new Result<>();

        result.setMessage(Message.Success.getType());
        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        List<TitleModel> titleModelList = titleDao.getAllTitleModelList(surveyId);
        int titleSequence = 0;
        if(Validator.isEmpty(titleModelList) || titleModelList.size() == 0){
            titleSequence = 1;
        }else{
            titleSequence = titleModelList.size()+1;
        }

        result.setData(titleSequence);

        return result;
    }

    private boolean updateOptionSequence(TitleModel<OptionModel> titleModel, Result result, SaveType saveType){

        if(titleModel.multipleType() || titleModel.singleType()){
            if(Validator.isEmpty(titleModel.getOptionModelList())){
                result.setMessage(Message.NotEmpty.getType());
                return false;
            }

            if(isOptionNameRepeat(titleModel)){
                result.setMessage(Message.RepeatOption.getType());
                return false;
            }
            int optionSequence = 1;
            for(OptionModel optionModel : titleModel.getOptionModelList()){
                if(saveType == SaveType.Create){
                    optionModel.setId(UUID.randomUUID().toString());
                    optionModel.setTitleId(titleModel.getId());
                }
                optionModel.setOptionSequence(optionSequence);
                optionSequence ++;
            }
        }

        return true;
    }

    private boolean isOptionNameRepeat(TitleModel<OptionModel> titleModel) {
        List<String> optionNameList = titleModel.getOptionModelList().stream().map(OptionModel::getOptionName)
                .collect(Collectors.toList());
        long count = optionNameList.stream().distinct().count();
        if (optionNameList.size() > count) {
            return true;
        }

        return false;
    }

    private boolean isTitleNameRepeat(List<TitleModel<OptionModel>> newTitleModelList, List<TitleModel> existedTitleModelList){
        List<String> newTitleNameList = newTitleModelList.stream().map(TitleModel::getTitle).collect(Collectors.toList());
        List<String> existedTitleNameList = existedTitleModelList.stream().map(TitleModel::getTitle).collect(Collectors.toList());
        List<String> titleNameList = newTitleNameList;
        titleNameList.addAll(existedTitleNameList);
        long count = titleNameList.stream().distinct().count();
        if(titleNameList.size() > count){
            return true;
        }

        return false;
    }

    private boolean isTitleSettingDuplicate(List<TitleModel> titleModelList, TitleModel titleModel){

        if(!Validator.isEmpty(titleModelList)){
            for(int i = 0; i < titleModelList.size(); i++) {
                if (!titleModelList.get(i).getId().equals(titleModel.getId())){
                   return true;
                }
            }
        }

        return false;
    }

    private void setOptionCorrected(List<TitleAnswerModel> titleAnswerModelList) {
        if(Validator.isEmpty(titleAnswerModelList)){
            return;
        }

        for(TitleAnswerModel titleAnswerModel : titleAnswerModelList){
            List<OptionModel> optionModelList = titleAnswerModel.getOptionModelList();
            List<AnswerModel> answerModelList = titleAnswerModel.getAnswerModelList();
            for(OptionModel optionModel : optionModelList){

                for(AnswerModel answerModel : answerModelList){
                    if(!Validator.isEmpty(answerModel.getAnswer()) && answerModel.getAnswer().equals(optionModel.getId())){
                        optionModel.setCorrected(true);
                        break;
                    }
                }
            }
        }

    }
}
