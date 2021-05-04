package com.sg.survey;

import com.alibaba.fastjson.JSON;
import com.sg.survey.submit.*;
import com.sg.survey.title.*;
import com.sg.survey.title.answer.AnswerModel;
import com.sg.survey.title.option.OptionDao;
import com.sg.survey.title.option.OptionModel;
import com.sg.survey.title.option.OptionStatisticsModel;
import com.sg.survey.title.option.OptionSummaryModel;
import com.sg.survey.util.UploadUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
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

    @Autowired
    private SubmitFileTitleDao submitFileTitleDao;

    @Autowired
    private TitleService titleService;

    @Override
    public Result getSurveyModelListByCreator(String creator){
        Result result = new Result();

        if(Validator.isEmpty(creator)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        List<SurveyModel> surveyModelList = surveyDao.querySurveyListByCreator(creator);
        if(!Validator.isEmpty(surveyModelList)){
            result.setData(surveyModelList);
            result.setMessage(Message.Success.getType());
        }else{
            result.setMessage(Message.SelectNoAnyRecord.getType());
        }

        return result;
    }

    @Override
    public Result getSurveyModelListByCreatorAndType(String creator, String surveyType) {
        Result result = new Result();

        if(Validator.isEmpty(creator)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        List<SurveyModel> surveyModelList = surveyDao.querySurveyListByCreatorAndType(creator, surveyType);
        if(!Validator.isEmpty(surveyModelList)){
            result.setData(surveyModelList);
            result.setMessage(Message.Success.getType());
        }else{
            result.setMessage(Message.SelectNoAnyRecord.getType());
        }

        return result;
    }

    @Override
    public Result getSurveyInfo(String surveyId) {
        Result result = new Result();


        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        SurveyModel surveyModel = surveyDao.queryById(surveyId);
        result.setData(surveyModel);
        result.setMessage(Message.Success.getType());

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
            e.printStackTrace();
            result.setData(null);
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    public Result getSurveyTitleOptionModel(String surveyId, String wxNickname, String wxOpenId) {
        Result result = new Result();

        if(Validator.isEmpty(surveyId)){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        SurveyTitleOptionModel<TitleInfoModel> surveyTitleOptionModel = null;
        try{
            surveyTitleOptionModel = surveyTitleOptionDao.getSurveyTitleOptionModelById(surveyId);
            this.initTextValue(surveyTitleOptionModel);
            List<SurveySubmitModel> surveySubmitModelList = null;
            surveySubmitModelList = surveySubmitDao.getSurveySubmitsByIdAndWxOpenId(surveyId, wxOpenId);
            if(Validator.isEmpty(surveySubmitModelList)){
                surveySubmitModelList = surveySubmitDao.getSurveySubmitsByIdAndWxNickname(surveyId, wxNickname);
            }
            if(!Validator.isEmpty(surveySubmitModelList)){
                String submitId = surveySubmitModelList.get(0).getId();
                List<SubmitSingleTitleModel> submitSingleTitleModelList = submitSingleTitleDao.querySubmitSingleTitleListBySubmitId(submitId);
                List<SubmitMultipleTitleModel> submitMultipleTitleModelList = submitMultipleTitleDao.querySubmitMultipleTitleModelGroupByTitleId(submitId);
                List<SubmitTextTitleModel> submitTextTitleModelList = submitTextTitleDao.querySubmitTextTitleListBySubmitId(submitId);
                List<SubmitFileTitleModel> submitFileTitleModelList = submitFileTitleDao.querySubmitFileTitleListBySubmitId(submitId);
                Map<String,SubmitTitleModel> submitTitleModelMap = convertSubmitTitleListToMap(submitSingleTitleModelList,submitMultipleTitleModelList,submitTextTitleModelList,submitFileTitleModelList);

                fillTitleAnswer(submitTitleModelMap, surveyTitleOptionModel);
            }
            result.setData(surveyTitleOptionModel);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            e.printStackTrace();
            result.setData(null);
            result.setMessage(Message.Error.getType());
        }

        return result;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel){
        Result result = new Result();
        if(Validator.isEmpty(surveyTitleOptionModel) || Validator.isEmpty(surveyTitleOptionModel.getTitleList())){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        return submitSurveyAnswer(surveyTitleOptionModel,null,null, result);
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result submitSurveyTitleOptionModel(SurveyTitleOptionModel surveyTitleOptionModel, String wxNickname, String wxOpenId) {
        Result result = new Result();

        if(Validator.isEmpty(surveyTitleOptionModel) || Validator.isEmpty(surveyTitleOptionModel.getTitleList())){
            result.setData(null);
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        // 先判断之前是否有提交，如果有提交过先删除
        List<SurveySubmitModel> submitTitleModelList = null;
        String surveyId = surveyTitleOptionModel.getId();
        submitTitleModelList = surveySubmitDao.getSurveySubmitsByIdAndWxOpenId(surveyId, wxOpenId);
        if(Validator.isEmpty(submitTitleModelList)){
            submitTitleModelList = surveySubmitDao.getSurveySubmitsByIdAndWxNickname(surveyId, wxNickname);
        }
        SurveySubmitModel currentSurveySubmitModel = null;
        if(!Validator.isEmpty(submitTitleModelList)){
            currentSurveySubmitModel = submitTitleModelList.get(0);
        }
        if(!Validator.isEmpty(currentSurveySubmitModel)){
            String submitId = currentSurveySubmitModel.getId();
            submitSingleTitleDao.deleteSubmitSingleTitleBySubmitId(submitId);
            submitMultipleTitleDao.deleteSubmitMultipleTitleBySubmitId(submitId);
            submitTextTitleDao.deleteSubmitTextTitleBySubmitId(submitId);
            submitFileTitleDao.deleteSubmitFileTitleBySubmitId(submitId);
            surveySubmitDao.deleteSurveySubmitById(submitId);
        }
        // 插入最新提交的答案

        return submitSurveyAnswer(surveyTitleOptionModel,wxNickname,wxOpenId,result);
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
            for(int i = 0; i < surveyTitleOptionSummaryModel.getTitleList().size(); i++){
                TitleSummaryModel titleSummaryModel = surveyTitleOptionSummaryModel.getTitleList().get(i);
                Collections.sort(titleSummaryModel.getOptionModelList());
            }
            result.setData(surveyTitleOptionSummaryModel);
            result.setMessage(Message.Success.getType());
        }catch (Exception e){
            e.printStackTrace();
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

        if(Validator.isEmpty(surveyModel) || Validator.isEmpty(surveyModel.getCreator()) || Validator.isEmpty(surveyModel.getSurveyName())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        try{
            List<SurveyModel> surveyModelList = surveyDao.queryBySurveyNameAndTypeAndCreator(surveyModel.getSurveyName(), surveyModel.getCreator(), surveyModel.getSurveyType());
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
            List<SurveyModel> surveyModelList = surveyDao.queryByNameAndType(surveyModel.getSurveyName(),surveyModel.getSurveyType());
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

        if(!Validator.isEmpty(titleModelList)){
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
        }

        return this.deleteSubmits(titleModelList,surveyId);

    }

    @Override
    public Result updateSurveyTimeSetting(SurveyModel surveyModel) {
        Result<SurveyModel> result = new Result<>();

        if(Validator.isEmpty(surveyModel) || Validator.isEmpty(surveyModel.getId()) || Validator.isEmpty(surveyModel.getBeginDateTime())|| Validator.isEmpty(surveyModel.getEndDateTime())){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        if(surveyModel.getBeginDateTime().compareTo(surveyModel.getEndDateTime()) >= 0){
            result.setMessage(Message.ParameterIllegal.toString());
            return result;
        }

        try{
            int rows = surveyDao.updateSurveyTimeSetting(surveyModel);
            if(rows <= 0){
                result.setMessage(Message.UpdateError.getType());
            }else{
                result.setData(surveyModel);
                result.setMessage(Message.Success.getType());
            }
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage(Message.Error.getType());
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result clearSurvey(String surveyId) {
        Result result = new Result();
        if (Validator.isEmpty(surveyId)) {
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }
        // 获取titleList
        List<TitleModel> titleModelList = titleDao.getAllTitleModelList(surveyId);

        return this.deleteSubmits(titleModelList, surveyId);
    }

    @Override
    public XSSFWorkbook createWorkbook(String surveyId, int sortCols, List<TitleModel> titleModelList) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = null;
        if(Validator.isEmpty(surveyId) || Validator.isEmpty(titleModelList)){
            return wb;
        }
        List<SurveyTitleOptionSubmitModel> surveyTitleOptionSubmitModelList = null;
        surveyTitleOptionSubmitModelList = surveyTitleOptionDao.getSurveySubmitDetailList(surveyId);
        if(Validator.isEmpty(surveyTitleOptionSubmitModelList)){
            return wb;
        }
        // 将每张答卷转化成Map形式（key为titleId,value为答案）
        List<Map<String,String>> submitList = new ArrayList<>();
        SurveyTitleOptionSubmitModel surveyTitleOptionSubmitModel;
        for(int i = 0; i < surveyTitleOptionSubmitModelList.size(); i++){
            surveyTitleOptionSubmitModel = surveyTitleOptionSubmitModelList.get(i);
            Map<String,String> submitMap = new HashMap<>();
            for(TitleInfoModel titleInfoModel : surveyTitleOptionSubmitModel.getTitleList()){

                if(titleInfoModel.singleType()){
                    List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                    if(!Validator.isEmpty(optionModelList)){
                        submitMap.put(titleInfoModel.getId(),optionModelList.get(0).getOptionName());
                    }
                }

                if(titleInfoModel.multipleType()){
                    List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                    if(!Validator.isEmpty(optionModelList)){
                        StringBuffer answer = new StringBuffer();
                        for(OptionModel optionModel : optionModelList){
                            answer.append(optionModel.getOptionName()).append("、");
                        }
                        submitMap.put(titleInfoModel.getId(),answer.toString());
                    }
                }

                if(titleInfoModel.textType()){
                    submitMap.put(titleInfoModel.getId(),titleInfoModel.getText());
                }

                if(titleInfoModel.imageType()){
                    submitMap.put(titleInfoModel.getId(), UploadUtil.getImageVisitUrl()+titleInfoModel.getText());
                }
            }
            submitList.add(submitMap);
        }
        // 多重排序
        for(int i = 0; i < titleModelList.size(); i++){
            String titleId = titleModelList.get(i).getId();
        }
        MultiColComparator multiColComparator = new MultiColComparator(titleModelList);
        Collections.sort(submitList,multiColComparator);
        // 创建工作表
        sheet = wb.createSheet(surveyTitleOptionSubmitModelList.get(0).getSurveyName());
        XSSFRow xssfRow;
        XSSFCell xssfCell;

        // 创建表头
        xssfRow = sheet.createRow(0);
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        for(int i = 0; i < titleModelList.size(); i++){
            xssfCell = xssfRow.createCell(i);
            xssfCell.setCellValue(titleModelList.get(i).getTitle());
            xssfCell.setCellStyle(cellStyle);
        }

        for(int i = 0; i < submitList.size(); i++){
            xssfRow = sheet.createRow(i+1);
            for(int j = 0; j < titleModelList.size(); j++){
                String titleId = titleModelList.get(j).getId();
                xssfCell = xssfRow.createCell(j);
                xssfCell.setCellValue(submitList.get(i).get(titleId));
            }
        }
        return wb;
    }

    @Override
    public Result contestTitleImport(MultipartFile file, String surveyId) {
        Result result = new Result();
        result.setMessage(Message.Success.getType());

        if(file.isEmpty()){
            result.setMessage(Message.NoExist.getType());
            return result;
        }

        if(Validator.isEmpty(surveyId)){
            result.setMessage(Message.NotEmpty.getType());
            return result;
        }

        List<TitleModel<OptionModel>> titleModelList = new ArrayList<>();
        List<AnswerModel> answerModelList = new ArrayList<>();

        result = readWorkbook(file,surveyId,titleModelList,answerModelList);
        if(!result.getMessage().equals(Message.Success.getType())){
            return result;
        }

        result = titleService.saveTitleAndAnswerModelList(titleModelList,answerModelList);

        System.out.println(result.getMessage());

        return result;
    }

    private Result readWorkbook(MultipartFile file, String surveyId, List<TitleModel<OptionModel>> titleModelList, List<AnswerModel> answerModelList){
        Result result = new Result();
        result.setMessage(Message.Success.getType());

        InputStream inputStream = null;
        Workbook workbook = null;
        Sheet sheet = null;

        TitleModel<OptionModel> titleModel = null;
        OptionModel optionModel = null;
        AnswerModel answerModel = null;

        int titleSequence = 1;
        int optionSequence = 0;
        int answerSequence = 0;
        boolean isAnswer = false;
        String optionName = null;

        Result<Integer> getStartedTitleSequenceResult = titleService.getStartedTitleSequence(surveyId);
        if(getStartedTitleSequenceResult.getMessage().equals(Message.Success.getType())){
            titleSequence = getStartedTitleSequenceResult.getData();
        }else {
            result.setMessage(getStartedTitleSequenceResult.getMessage());
            return result;
        }
        try {
            inputStream = file.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(0);
            for(int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++){
                Row row = sheet.getRow(rowIndex);
                if(row == null){
                    continue;
                }
                Cell cell = row.getCell(0);
                String cellVal = getString(cell);
                if(Validator.isEmpty(cellVal)){
                    result.setMessage(Message.NotEmpty.getType());
                    return result;
                }

                // 如果是题目
                if(cellVal.startsWith(TitleFlag.Multiple.getFlag()) || cellVal.startsWith(TitleFlag.Single.getFlag()) || cellVal.startsWith(TitleFlag.Text.getFlag())){
                    titleModel = new TitleModel<OptionModel>();
                    titleModel.setId(UUID.randomUUID().toString());
                    titleModel.setTitle(cellVal.substring(2));
                    titleModel.setSurveyId(surveyId);
                    titleModel.setTitleSequence(titleSequence);
                    titleModel.setRequired(RequiredFlag.False.getFlag());
                    titleModel.setIsNameColumn(NameColumn.NO.getVal());
                    if(cellVal.startsWith(TitleFlag.Multiple.getFlag())){
                        titleModel.setTitleType(TitleType.MultipleTitle.getVal());
                    }
                    if(cellVal.startsWith(TitleFlag.Single.getFlag())){
                        titleModel.setTitleType(TitleType.SingleTitle.getVal());
                    }
                    if(cellVal.startsWith(TitleFlag.Text.getFlag())){
                        titleModel.setTitleType(TitleType.Text.getVal());
                    }
                    titleSequence++;
                    titleModelList.add(titleModel);
                    optionSequence = 1;
                    answerSequence = 1;
                }else{// 如果是选项或者答案
                    if(titleModel.multipleType() || titleModel.singleType()){ // 如果是多选题或者是单选题
                        optionName = cellVal;
                        isAnswer = false;
                        if(Validator.isEmpty(titleModel.getOptionModelList())){
                            titleModel.setOptionModelList(new ArrayList());
                        }
                        if(cellVal.endsWith(AnswerFlag.True.getFlag())){
                            isAnswer = true;
                            optionName = cellVal.substring(0,cellVal.length()-1);
                        }

                        optionModel = new OptionModel(UUID.randomUUID().toString(),titleModel.getId(),optionName,optionSequence);
                        titleModel.getOptionModelList().add(optionModel);
                        optionSequence ++;
                        if(isAnswer){
                            answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),optionModel.getId(),answerSequence);
                            answerModelList.add(answerModel);
                            answerSequence ++;
                        }
                    }else{ // 如果是填空题
                        answerModel = new AnswerModel(UUID.randomUUID().toString(),titleModel.getId(),cellVal,answerSequence);
                        answerModelList.add(answerModel);
                        answerSequence ++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(Message.Error.getType());
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    result.setMessage(Message.FileStreamCloseFail.getType());
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private  String getString(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }

    private Map<String, SubmitTitleModel> convertSubmitTitleListToMap(List<SubmitTitleModel> submitTitleModelList, Map<String, SubmitTitleModel> submitTitleModelMap){
        if(Validator.isEmpty(submitTitleModelMap)){
            submitTitleModelMap = new HashMap<>();
        }

        for(SubmitTitleModel submitTitleModel : submitTitleModelList){
            submitTitleModelMap.put(submitTitleModel.getTitleId(),submitTitleModel);
        }

        return submitTitleModelMap;
    }

    private Map<String, SubmitTitleModel> convertSubmitTitleListToMap(List<SubmitSingleTitleModel> submitSingleTitleModelList, List<SubmitMultipleTitleModel> submitMultipleTitleModelList, List<SubmitTextTitleModel> submitTextTitleModelList,  List<SubmitFileTitleModel> submitFileTitleModelList) {
        Map<String, SubmitTitleModel> submitTitleModelMap = new HashMap<>();

        for(SubmitSingleTitleModel submitSingleTitleModel : submitSingleTitleModelList){
            submitTitleModelMap.put(submitSingleTitleModel.getTitleId(), submitSingleTitleModel);
        }
        for(SubmitMultipleTitleModel submitMultipleTitleModel : submitMultipleTitleModelList){
            submitTitleModelMap.put(submitMultipleTitleModel.getTitleId(), submitMultipleTitleModel);
        }
        for(SubmitTextTitleModel submitTextTitleModel : submitTextTitleModelList){
            submitTitleModelMap.put(submitTextTitleModel.getTitleId(), submitTextTitleModel);
        }

        for(SubmitFileTitleModel submitFileTitleModel : submitFileTitleModelList){
            submitTitleModelMap.put(submitFileTitleModel.getTitleId(), submitFileTitleModel);
        }

        return submitTitleModelMap;
    }

    private void fillTitleAnswer(Map<String, SubmitTitleModel> submitTitleModelMap, SurveyTitleOptionModel<TitleInfoModel> surveyTitleOptionModel) {
        List<TitleInfoModel> titleInfoModelList = surveyTitleOptionModel.getTitleList();
        if(Validator.isEmpty(titleInfoModelList)){
            return;
        }
        for(TitleInfoModel titleInfoModel : titleInfoModelList){
            String titleId = titleInfoModel.getId();
            SubmitTitleModel submitTitleModel = submitTitleModelMap.get(titleId);
            if(Validator.isEmpty(submitTitleModel)){
                continue;
            }
            if(titleInfoModel.singleType()){
                List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                String selectedOptionId = ((SubmitSingleTitleModel)submitTitleModel).getOptionId();
                for(OptionModel optionModel : optionModelList){
                    if(optionModel.getId().equals(selectedOptionId)){
                        optionModel.setSelected(true);
                        break;
                    }
                }
            }

            if(titleInfoModel.multipleType()){
                List<OptionModel> optionModelList = titleInfoModel.getOptionModelList();
                List<String> optionIdList = ((SubmitMultipleTitleModel)submitTitleModel).getOptionIdList();
                for(OptionModel optionModel : optionModelList){
                    for(String selectedOptionId : optionIdList){
                        if (optionModel.getId().equals(selectedOptionId)){
                            optionModel.setSelected(true);
                        }
                    }
                }
            }

            if(titleInfoModel.textType() ){
                String text = ((SubmitTextTitleModel) submitTitleModel).getText();
                titleInfoModel.setText(text);
            }

            if(titleInfoModel.imageType()){
                String fileName = ((SubmitFileTitleModel) submitTitleModel).getFileName();
                titleInfoModel.setText(fileName);
            }
        }
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


                // 如果是填空题 或者文件题
                if(titleStatisticsModel.textType() || titleStatisticsModel.imageType()){
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
                        textNameList.add(textNameModel);
                    }else {
                        if(!Validator.isEmpty(text)){
                            textNameModel = new TextNameModel(text,name);
                            textNameList.add(textNameModel);
                        }
                    }
                }

                // 如果是选择题
                if(titleStatisticsModel.multipleType() || titleStatisticsModel.singleType()){
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

        //  对名字进行排序
        for(TitleStatisticsModel titleStatisticsModel : surveyTitleOptionModel.getTitleList()){
            String titleId = titleStatisticsModel.getId();
            String titleType = titleStatisticsModel.getTitleType();

            if(titleStatisticsModel.textType() || titleStatisticsModel.imageType()) {
                List<TextNameModel> textNameList = titleStatisticsModel.getTextNameList();
                if (textNameList != null) {
                    Collections.sort(textNameList);
                }
            }

            if(titleStatisticsModel.multipleType() || titleStatisticsModel.singleType()){
                List<OptionStatisticsModel> optionStatisticsList = titleStatisticsModel.getOptionModelList();
                if(Validator.isEmpty(optionStatisticsList)){
                    continue;
                }
                for(OptionStatisticsModel optionStatisticsModel : optionStatisticsList){
                    List<String> nameList = optionStatisticsModel.getNameList();
                    if(!Validator.isEmpty(nameList)){
                        Collections.sort(nameList,Collator.getInstance(Locale.CHINA));
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
                if(submitTitleModel.textType() || submitTitleModel.imageType()){
                    titleAnswerMap.put(submitTitleModel.getId(),submitTitleModel.getText());
                }

                if(submitTitleModel.singleType() || submitTitleModel.multipleType()){
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


    private Map<String,List<TitleModel>> getTitleModelMap(List<TitleModel> titleModelList){
        Map<String,List<TitleModel>> titleModelMap = new HashMap<>();
        List<TitleModel> singleTitleModelList = new ArrayList<>();
        List<TitleModel> multipleTitleModelList = new ArrayList<>();
        List<TitleModel> textTitleModelList = new ArrayList<>();
        List<TitleModel> imageTitleModelList = new ArrayList<>();
        if(!Validator.isEmpty(titleModelList)) {
            for (int i = 0; i < titleModelList.size(); i++) {
                if (titleModelList.get(i).singleType()) {
                    singleTitleModelList.add(titleModelList.get(i));
                } else if (titleModelList.get(i).multipleType()) {
                    multipleTitleModelList.add(titleModelList.get(i));
                } else if(titleModelList.get(i).textType()){
                    textTitleModelList.add(titleModelList.get(i));
                }else{
                    imageTitleModelList.add(titleModelList.get(i));
                }
            }
        }

        titleModelMap.put(TitleType.SingleTitle.getVal(),singleTitleModelList);
        titleModelMap.put(TitleType.MultipleTitle.getVal(),multipleTitleModelList);
        titleModelMap.put(TitleType.Text.getVal(),textTitleModelList);
        titleModelMap.put(TitleType.Image.getVal(),imageTitleModelList);

        return titleModelMap;
    }


    private Result deleteSubmits(List<TitleModel> titleModelList, String surveyId){
        Result result = new Result();

        Map<String,List<TitleModel>> titleModelMap = this.getTitleModelMap(titleModelList);
        List<TitleModel> singleTitleModelList = titleModelMap.get(TitleType.SingleTitle.getVal());
        List<TitleModel> multipleTitleModelList = titleModelMap.get(TitleType.MultipleTitle.getVal());
        List<TitleModel> textTitleModelList = titleModelMap.get(TitleType.Text.getVal());
        List<TitleModel> imageTitleModelList = titleModelMap.get(TitleType.Image.getVal());

        //删除surveySubmit
        int rows = surveySubmitDao.deleteSurveySubmitsBySurveyId(surveyId);
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

        // 删除image
        if(!Validator.isEmpty(imageTitleModelList)){
            rows = submitFileTitleDao.deleteSubmitFileByTitleId(imageTitleModelList);
            if(rows < 0){
                result.setMessage(Message.DeleteError.getType());
                return result;
            }
        }

        result.setMessage(Message.Success.getType());

        return result;
    }


    private Result submitSurveyAnswer(SurveyTitleOptionModel surveyTitleOptionModel,String wxNickname,String wxOpenId,Result result) {
        List<TitleInfoModel> titleModelList = surveyTitleOptionModel.getTitleList();
        List<SubmitOptionModel> submitSingleModelList = new ArrayList<>();
        List<SubmitOptionModel> submitMultipleModelList = new ArrayList<>();
        List<SubmitTextTitleModel> submitTextTitleModelList = new ArrayList<>();
        List<SubmitFileTitleModel> submitFileTitleModelList = new ArrayList<>();

        SurveySubmitModel surveySubmitModel = null;
        surveySubmitModel = new SurveySubmitModel(UUID.randomUUID().toString(),surveyTitleOptionModel.getId(),"",Validator.isEmpty(wxNickname)?"":wxNickname, Validator.isEmpty(wxOpenId)?"":wxOpenId);
        TitleInfoModel titleInfoModel = null;
        for(int i = 0; i < titleModelList.size(); i++){
            titleInfoModel = titleModelList.get(i);
            if(titleInfoModel.textType()){
                SubmitTextTitleModel submitTextTitleModel = new SubmitTextTitleModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleInfoModel.getId(),titleInfoModel.getText());
                submitTextTitleModelList.add(submitTextTitleModel);
            }else if(titleInfoModel.singleType()){
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
            }else if(titleInfoModel.multipleType()){
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
            }else{
                SubmitFileTitleModel submitFileTitleModel = new SubmitFileTitleModel(UUID.randomUUID().toString(),surveySubmitModel.getId(),titleInfoModel.getId(),titleInfoModel.getText());
                submitFileTitleModelList.add(submitFileTitleModel);
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

        if(!Validator.isEmpty(submitFileTitleModelList)){
            submitFileTitleDao.insertSubmitFileTitleList(submitFileTitleModelList);
        }

        result.setData(surveyTitleOptionModel);
        result.setMessage(Message.Success.getType());
        return result;
    }

    private class MultiColComparator implements Comparator<Map<String,String>> {

        List<TitleModel> titleModelList;

        public MultiColComparator(List<TitleModel> titleModelList){
            this.titleModelList = titleModelList;
        }

        @Override
        public int compare(Map<String, String> o1, Map<String, String> o2){

            String titleId = "";
            for(TitleModel titleModel : titleModelList){
                titleId = titleModel.getId();
                if(!o1.get(titleId).equals(o2.get(titleId))){
                    return o1.get(titleId).compareTo(o2.get(titleId));
                }

            }

            return o1.get(titleId).compareTo(o2.get(titleId));
        }
    }
}
