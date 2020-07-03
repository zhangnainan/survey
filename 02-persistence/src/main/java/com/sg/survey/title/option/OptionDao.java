package com.sg.survey.title.option;

import com.sg.survey.title.SurveyTitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface OptionDao {

    public int insertOption(OptionModel optionModel);

    public int insertOptionList(List<OptionModel> optionModelList);

    public int deleteOptionByTitleId(String titleId);

    public int deleteOptionByTitleIds(List<String> titleIdList);

    public int updateOption(OptionModel optionModel);

    public int updateOptionList(List<OptionModel> optionModelList);

    public List<OptionModel> queryOptionListByTitleIds(List<SurveyTitleModel> surveyTitleModelList);


}
