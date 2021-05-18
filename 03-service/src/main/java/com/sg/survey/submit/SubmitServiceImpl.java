package com.sg.survey.submit;

import com.sg.survey.Message;
import com.sg.survey.Result;
import com.sg.survey.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("com.sg.survey.submit.service.impl")
public class SubmitServiceImpl implements SubmitService {

    @Autowired
    private SurveySubmitDao surveySubmitDao;

    @Override
    public Result getSurveySubmitList(String surveyId, String wxOpenId) {
        Result result = new Result();

        result.setMessage(Message.Error.getType());
        if(Validator.isEmpty(surveyId) || Validator.isEmpty(wxOpenId)){
            result.setMessage(Message.ParameterIllegal.getType());
            return result;
        }

        List<SurveySubmitModel> surveySubmitModelList = surveySubmitDao.getSurveySubmitsByIdAndWxOpenId(surveyId, wxOpenId);
        result.setData(surveySubmitModelList);
        result.setMessage(Message.Success.getType());

        return result;
    }

    @Override
    public Result getContestRankPage(String surveyId, int pageSize, int start) {
        Result result = new Result();

        try{
            result.setMessage(Message.Error.getType());
            if(Validator.isEmpty(surveyId) || pageSize <= 0 || start <0){
                result.setMessage(Message.ParameterIllegal.getType());
                return result;
            }

            List<SurveySubmitModel> surveySubmitList = surveySubmitDao.getContestRankPage(surveyId,pageSize,start);
            result.setData(surveySubmitList);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }


        return result;
    }

    @Override
    public Result getSubmitCount(String surveyId) {
        Result result = new Result();

        result.setMessage(Message.Error.getType());
        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.ParameterIllegal.getType());
            return result;
        }
        Integer submitCount = surveySubmitDao.getSubmitCount(surveyId);
        result.setData(submitCount);
        result.setMessage(Message.Success.getType());

        return result;
    }

    @Override
    public List<SurveySubmitSummaryModel> getSurveySubmitSummaryModelList(String surveyId) {

        List<SurveySubmitSummaryModel> surveySubmitSummaryModelList = null;

        if(Validator.isEmpty(surveyId)){
            return null;
        }

        surveySubmitSummaryModelList = surveySubmitDao.getSurveySubmitSummaryList(surveyId);
        if(Validator.isEmpty(surveySubmitSummaryModelList)){
            return surveySubmitSummaryModelList;
        }
        List<SubmitTitleAnswerModel> submitTitleAnswerModelList = surveySubmitDao.getSubmitTitleAnswerListBySubmitIds(surveySubmitSummaryModelList);

        if(Validator.isEmpty(submitTitleAnswerModelList)){
            return surveySubmitSummaryModelList;
        }

        // 初始化SubmitTitleAnswerModelList
        for(SurveySubmitSummaryModel surveySubmitSummaryModel : surveySubmitSummaryModelList){
            surveySubmitSummaryModel.setSubmitTitleAnswerModelList(new ArrayList<>());
        }

        for(SubmitTitleAnswerModel submitTitleAnswerModel : submitTitleAnswerModelList){
            String submitId = submitTitleAnswerModel.getSubmitId();
            for(SurveySubmitSummaryModel surveySubmitSummaryModel : surveySubmitSummaryModelList){
                if(surveySubmitSummaryModel.getId().equals(submitId)){
                    surveySubmitSummaryModel.getSubmitTitleAnswerModelList().add(submitTitleAnswerModel);
                }
            }
        }

        return surveySubmitSummaryModelList;
    }
}
