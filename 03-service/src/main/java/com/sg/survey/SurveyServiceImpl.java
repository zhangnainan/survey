package com.sg.survey;

import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.sg.survey.submit.*;
import com.sg.survey.title.*;
import com.sg.survey.title.option.OptionDao;
import com.sg.survey.title.option.OptionModel;
import com.sg.survey.title.option.OptionStatisticsModel;
import com.sg.survey.title.option.OptionSummaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by jiuge on 2020/5/20.
 */
@Service("survey.survey.service.impl")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyDao surveyDao;

    @Autowired
    private SurveyTitleOptionDao surveyTitleOptionDao;

    @Autowired
    private SurveyTitleDao surveyTitleDao;

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private SurveySubmitDao surveySubmitDao;

    @Autowired
    private SubmitMultipleTitleDao submitMultipleTitleDao;

    @Autowired
    private SubmitSingleTitleDao submitSingleTitleDao;

    @Autowired
    private SubmitTextTitleDao submitTextTitleDao;

    @Override
    public Result getSurveyModelList(){
        Result result = new Result();
        List<SurveyModel> surveyModelList = surveyDao.queryAll();
        if(!Validator.isEmpty(surveyModelList)){
            result.setData(surveyModelList);
            result.setMessage(Message.Success.getType());
        }else{
            result.setMessage(Message.SelectNoAnyRecord.getType());
        }
        return result;
    }

    @Override
    public Result getSurveyTitleOptionModel(String surveyId){

        Result<SurveyTitleOptionModel<TitleInfoModel>> result = new Result();


        if(Validator.isEmpty(surveyId)){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        SurveyTitleOptionModel<TitleInfoModel> surveyTitleOptionModel = null;
        try{
            surveyTitleOptionModel = surveyTitleOptionDao.getSurveyTitleOptionModelById(surveyId);
            this.initTextValue(surveyTitleOptionModel);
            result.setData(surveyTitleOptionModel);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setData(null);
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel){
        Result result = new Result();
        List<TitleInfoModel> titleModelList = surveyTitleOptionModel.getTitleList();
        List<SubmitOptionModel> submitSingleModelList = new ArrayList<>();
        List<SubmitOptionModel> submitMultipleModelList = new ArrayList<>();
        List<SubmitTextTitleModel> submitTextTitleModelList = new ArrayList<>();

        if(Validator.isEmpty(surveyTitleOptionModel) || Validator.isEmpty(titleModelList)){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        SurveySubmitModel surveySubmitModel = new SurveySubmitModel(UUID.randomUUID().toString(),surveyTitleOptionModel.getId(),"");
        TitleInfoModel titleInfoModel = null;
        for(int i = 0; i < titleModelList.size(); i++){
            titleInfoModel = titleModelList.get(i);
            if(titleInfoModel.getTitleType().equals(TitleType.Text.getVal())){
                SubmitTextTitleModel submitTextTitleModel = new SubmitTextTitleModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleInfoModel.getId(),titleInfoModel.getText());
                submitTextTitleModelList.add(submitTextTitleModel);
            }else if(titleInfoModel.getTitleType().equals(TitleType.SingleTitle.getVal())){
                List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                if(Validator.isEmpty(optionModelList)){
                    continue;
                }
                for(OptionModel optionModel:optionModelList){
                    if(optionModel.isSelected()){
                        SubmitOptionModel submitOptionModel = new SubmitOptionModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleInfoModel.getId(),optionModel.getId());
                        submitSingleModelList.add(submitOptionModel);
                        break;
                    }
                }
            }else if(titleInfoModel.getTitleType().equals(TitleType.MultipleTitle.getVal())){
                List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                if(Validator.isEmpty(optionModelList)){
                    continue;
                }
                for(OptionModel optionModel:optionModelList){
                    if(optionModel.isSelected()){
                        SubmitOptionModel submitOptionModel = new SubmitOptionModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleInfoModel.getId(),optionModel.getId());
                        submitMultipleModelList.add(submitOptionModel);
                    }
                }
            }
        }


        surveySubmitDao.insertSurveySubmit(surveySubmitModel);
        if(!Validator.isEmpty(submitSingleModelList)){
            submitSingleTitleDao.insertSubmitSingleTitleList(submitSingleModelList);
        }
        if(!Validator.isEmpty(submitMultipleModelList)){
            submitMultipleTitleDao.insertSubmitMultipleTitleList(submitMultipleModelList);
        }

        if(!Validator.isEmpty(submitTextTitleModelList)){
            submitTextTitleDao.insertSubmitTextTitleList(submitTextTitleModelList);
        }

        result.setData(surveyTitleOptionModel);
        result.setMessage(Message.Success.getType());
        return result;
    }

    @Override
    public Result getSurveySummary(String surveyId){
        Result result = new Result();
        SurveyTitleOptionSummaryModel surveyTitleOptionSummaryModel = null;
        if(Validator.isEmpty(surveyId)){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            surveyTitleOptionSummaryModel = surveyTitleOptionDao.getSurveySubmitSummary(surveyId);
            submitAndPercentCount(surveyTitleOptionSummaryModel);
            Collections.sort(surveyTitleOptionSummaryModel.getTitleList());
            result.setData(surveyTitleOptionSummaryModel);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public Result getSurveySubmitDetailList(String surveyId){
        Result result = new Result();
        List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList = null;
        if(Validator.isEmpty(surveyId)){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            surveyTitleOptionSubmitModelList = surveyTitleOptionDao.getSurveySubmitDetailList(surveyId);
            this.titleSort(surveyTitleOptionSubmitModelList);
            result.setData(surveyTitleOptionSubmitModelList);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public Result getSurveyByNameStatistics(String surveyId, String statisticsTitleId){
        Result<SurveyTitleOptionModel> result = new Result();

        if(Validator.isEmpty(surveyId) || Validator.isEmpty(statisticsTitleId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        SurveyTitleOptionStatisticsModel surveyTitleOptionStatisticsModel = null;
        List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList = null;
        try{
            surveyTitleOptionStatisticsModel = surveyTitleOptionDao.getSurveyStatistics(surveyId);
            surveyTitleOptionSubmitModelList = surveyTitleOptionDao.getSurveySubmitDetailList(surveyId);
            this.titleSort(surveyTitleOptionSubmitModelList);
            statistics(surveyTitleOptionStatisticsModel,surveyTitleOptionSubmitModelList,statisticsTitleId);
            result.setData(surveyTitleOptionStatisticsModel);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    public Result saveSurvey(SurveyModel surveyModel) {
        Result<SurveyModel> result = new Result<>();

        if(Validator.isEmpty(surveyModel) || Validator.isEmpty(surveyModel.getSurveyName())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            List<SurveyModel> surveyModelList = surveyDao.queryByName(surveyModel.getSurveyName());
            if(!Validator.isEmpty(surveyModelList)){
                result.setMessage(Message.Exist.getType());
                return result;
            }

            surveyModel.setId(UUID.randomUUID().toString());
            int rows = surveyDao.saveSurvey(surveyModel);
            if(rows <= 0){
                result.setMessage(Message.AddError.getType());
            }else{
                result.setData(surveyModel);
                result.setMessage(Message.Success.getType());
            }

        }catch (Exception e){
            result.setMessage(Message.Error.getType());
        }


        return result;
    }

    @Override
    public Result updateSurvey(SurveyModel surveyModel) {
        Result<SurveyModel> result = new Result<>();

        if(Validator.isEmpty(surveyModel) || Validator.isEmpty(surveyModel.getSurveyName())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            List<SurveyModel> surveyModelList = surveyDao.queryByName(surveyModel.getSurveyName());
            if(!Validator.isEmpty(surveyModelList)){
                for(SurveyModel tempSurveyModel : surveyModelList){
                    if (!tempSurveyModel.getId().equals(surveyModel.getId())) {
                        result.setMessage(Message.Exist.getType());
                        return result;
                    }
                }
            }

            int rows = surveyDao.updateSurvey(surveyModel);
            if(rows <= 0){
                result.setMessage(Message.UpdateError.getType());
            }else{
                result.setData(surveyModel);
                result.setMessage(Message.Success.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result deleteSurvey(String surveyId) {
        Result result = new Result();
        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        // 获取titleList
        List<TitleModel> titleModelList = titleDao.getAllTitleModelList(surveyId);

        // 删除survey
        int rows = surveyDao.deleteSurvey(surveyId);
        if(rows < 0){
            result.setMessage(Message.DeleteError.getType());
            return result;
        }

        //删除surveySubmit
        rows = surveySubmitDao.deleteSurveySubmitsBySurveyId(surveyId);
        if(rows < 0){
            result.setMessage(Message.DeleteError.getType());
            return result;
        }

        if(!Validator.isEmpty(titleModelList)){
            List<TitleModel> singleTitleModelList = new ArrayList<>();
            List<TitleModel> multipleTitleModelList = new ArrayList<>();
            List<TitleModel> textTitleModelList = new ArrayList<>();
            for(int i = 0; i < titleModelList.size(); i++){
                if(titleModelList.get(i).getTitleType().equals(TitleType.SingleTitle.getVal())){
                    singleTitleModelList.add(titleModelList.get(i));
                }else if(titleModelList.get(i).getTitleType().equals(TitleType.MultipleTitle.getVal())){
                    multipleTitleModelList.add(titleModelList.get(i));
                }else{
                    textTitleModelList.add(titleModelList.get(i));
                }
            }

            // 删除titleModel
            rows = titleDao.deleteTitleModelList(titleModelList);
            if(rows < 0){
                result.setMessage(Message.DeleteError.getType());
                return result;
            }
            // 删除optionModel
            rows = optionDao.deleteOptionByTitleIds(titleModelList);
            if(rows < 0){
                result.setMessage(Message.DeleteError.getType());
                return result;
            }
            // 删除singleSubmits
            if(!Validator.isEmpty(singleTitleModelList)){
                rows = submitSingleTitleDao.deleteSubmitSingleByTitleId(singleTitleModelList);
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }
            }
            // 删除multipleSubmits
            if(!Validator.isEmpty(multipleTitleModelList)){
                rows = submitMultipleTitleDao.deleteSubmitMultipleByTitleId(multipleTitleModelList);
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }
            }
            // 删除textSubmits
            if(!Validator.isEmpty(textTitleModelList)){
                rows = submitTextTitleDao.deleteSubmitTextByTitleId(textTitleModelList);
                if(rows < 0){
                    result.setMessage(Message.DeleteError.getType());
                    return result;
                }
            }

        }
        result.setMessage(Message.Success.getType());

        return result;
    }

    private void initTextValue(SurveyTitleOptionModel<TitleInfoModel> surveyTitleOptionModel){
        if(Validator.isEmpty(surveyTitleOptionModel)){
            return;
        }

        if (Validator.isEmpty(surveyTitleOptionModel.getTitleList())){
            return;
        }

        List<TitleInfoModel> titleList = surveyTitleOptionModel.getTitleList();

        for(TitleInfoModel titleInfoModel : titleList){
            titleInfoModel.setText("");
        }
    }

    private void statistics(SurveyTitleOptionStatisticsModel surveyTitleOptionModel,List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList,String statisticsTitleId){

        if(Validator.isEmpty(surveyTitleOptionModel) || Validator.isEmpty(surveyTitleOptionSubmitModelList) || Validator.isEmpty(statisticsTitleId)){
            return;
        }
        // 将答卷列表转换成Map形式
        Map<String,Map<String,Object>> submitSurveysMap = convertSurveySubmitListToMap(surveyTitleOptionSubmitModelList);
        // 统计信息
        // 统计答卷数
        surveyTitleOptionModel.setSubmitCount(surveyTitleOptionSubmitModelList.size());
        // 统计各答卷信息
        Iterator<String> iterator = submitSurveysMap.keySet().iterator();
        while (iterator.hasNext()){
            String submitId = iterator.next();
            // 答卷
            Map<String,Object> submitSurveyMap = submitSurveysMap.get(submitId);
            String name = "";
            if(!submitSurveyMap.containsKey(statisticsTitleId)){
                continue;
            }else {
                name = (String) submitSurveyMap.get(statisticsTitleId);
                if(Validator.isEmpty(name)){
                    continue;
                }
            }
            // 答卷汇总
            for(TitleStatisticsModel titleStatisticsModel : surveyTitleOptionModel.getTitleList()){
                String titleId = titleStatisticsModel.getId();
                // 判断答卷中是否有此题
                if(! submitSurveyMap.containsKey(titleId)){
                    continue;
                }
                String titleType = titleStatisticsModel.getTitleType();

                // 如果是填空题
                if(titleType.equals(TitleType.Text.getVal())){
                    String text = (String) submitSurveyMap.get(titleId);
                    List<TextNameModel> textNameList =  titleStatisticsModel.getTextNameList();
                    if(textNameList == null){
                        textNameList = new ArrayList<>();
                        titleStatisticsModel.setTextNameList(textNameList);
                    }
                    TextNameModel textNameModel = null;
                    if(titleId.equals(statisticsTitleId)){
                        textNameModel = new TextNameModel();
                        textNameModel.setName(name);
                    }else {
                        textNameModel = new TextNameModel(text,name);
                    }
                    textNameList.add(textNameModel);
                }

                // 如果是选择题
                if(titleType.equals(TitleType.MultipleTitle.getVal()) || titleType.equals(TitleType.SingleTitle.getVal())){
                    List<OptionStatisticsModel> optionStatisticsList = titleStatisticsModel.getOptionModelList();
                    Map<String,String> optionMap = (Map<String,String>)submitSurveyMap.get(titleId);
                    if(Validator.isEmpty(optionStatisticsList)){
                        continue;
                    }
                    for(OptionStatisticsModel optionStatisticsModel : optionStatisticsList){
                        String optionId = optionStatisticsModel.getId();
                        if(!optionMap.containsKey(optionId)){
                            continue;
                        }
                        List<String> nameList = optionStatisticsModel.getNameList();
                        if(nameList == null){
                            nameList = new ArrayList<>();
                            optionStatisticsModel.setNameList(nameList);
                        }
                        nameList.add(name);
                    }
                }
            }
        }
    }

    private Map<String,Map<String,Object>> convertSurveySubmitListToMap(List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList){
        Map<String,Map<String,Object>> submitSurveysMap = new HashMap<>();
        for(SurveyTitleOptionSubmitModel submitSurveyModel : surveyTitleOptionSubmitModelList){
            List<TitleInfoModel> submitTitleList = submitSurveyModel.getTitleList();
            if(Validator.isEmpty(submitTitleList)){
                continue;
            }
            String submitId = submitSurveyModel.getSubmitId();
            Map<String,Object> titleAnswerMap = new HashMap<>();
            submitSurveysMap.put(submitId,titleAnswerMap);
            for(TitleInfoModel submitTitleModel : submitTitleList){
                String titleType = submitTitleModel.getTitleType();
                if(titleType.equals(TitleType.Text.getVal())){
                    titleAnswerMap.put(submitTitleModel.getId(),submitTitleModel.getText());
                }

                if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
                    List<OptionModel> optionModelList = submitTitleModel.getOptionModelList();
                    if(Validator.isEmpty(optionModelList)){
                        continue;
                    }

                    Map<String,String> optionMap = new HashMap<>();
                    titleAnswerMap.put(submitTitleModel.getId(),optionMap);
                    for(OptionModel optionModel : optionModelList){
                        optionMap.put(optionModel.getId(),optionModel.getOptionName());
                    }
                }
            }
        }

        return submitSurveysMap;
    }

    private void titleSort(List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList){
        if(!Validator.isEmpty(surveyTitleOptionSubmitModelList)){
            for(int  i = 0; i < surveyTitleOptionSubmitModelList.size(); i++){
                List<TitleInfoModel> titleInfoModelList = surveyTitleOptionSubmitModelList.get(i).getTitleList();
                if(!Validator.isEmpty(titleInfoModelList)){
                    Collections.sort(titleInfoModelList);
                }
            }
        }
    }

    private void submitAndPercentCount(SurveyTitleOptionSummaryModel surveyTitleOptionSummaryModel){
        int totalSubmit = surveyTitleOptionSummaryModel.getSubmitSummaryModelList().size();
        if(totalSubmit == 0){
            return;
        }

        for(TitleSummaryModel titleSummaryModel : surveyTitleOptionSummaryModel.getTitleList()){
            String titleType = titleSummaryModel.getTitleType();
            if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
                for(SubmitTitleAnswerModel submitTitleAnswerModel : titleSummaryModel.getSubmitTitleAnswerModelList()){
                    for(OptionSummaryModel optionSummaryModel : titleSummaryModel.getOptionModelList()){
                        if(optionSummaryModel.getId().equals(submitTitleAnswerModel.getAnswer())){
                            int submitCount = optionSummaryModel.getSubmitCount();
                            submitCount += 1;
                            optionSummaryModel.setSubmitCount(submitCount);
                        }
                    }
                }
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##%");
        for(TitleSummaryModel titleSummaryModel : surveyTitleOptionSummaryModel.getTitleList()){
            String titleType = titleSummaryModel.getTitleType();
            if(titleType.equals(TitleType.SingleTitle.getVal()) || titleType.equals(TitleType.MultipleTitle.getVal())){
                for (OptionSummaryModel optionSummaryModel : titleSummaryModel.getOptionModelList()){
                    String percent = decimalFormat.format(((float)optionSummaryModel.getSubmitCount())/totalSubmit);
                    optionSummaryModel.setPercent(percent);
                }
            }
        }
    }

}
