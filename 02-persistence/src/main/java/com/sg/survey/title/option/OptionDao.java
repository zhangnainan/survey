package com.sg.survey.title.option;

import com.sg.survey.submit.SubmitOptionModel;
import com.sg.survey.title.SurveyTitleModel;
import com.sg.survey.title.TitleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface OptionDao {

    public int insertOption(OptionModel optionModel);

    public int insertOptionList(List<OptionModel> optionModelList);

    public int deleteOptionModel(String optionId);

    public int deleteOptionByTitleId(String titleId);

    public int deleteOptionByTitleIds(List<TitleModel> titleModelList);

    public int deleteOptionModelList(List<OptionModel> optionModelList);

    public int updateOption(OptionModel optionModel);

    public int updateOptionList(List<OptionModel> optionModelList);

    public List<OptionModel> queryOptionListByTitleIds(List<SurveyTitleModel> surveyTitleModelList);

    public List<SubmitOptionModel> querySubmitOptionList(@Param("optionId") String optionId,@Param("titleType") String titleType);


}
