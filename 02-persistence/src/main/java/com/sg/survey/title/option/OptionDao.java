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

    int insertOption(OptionModel optionModel);

    int insertOptionList(List<OptionModel> optionModelList);

    int deleteOptionModel(String optionId);

    int deleteOptionByTitleId(String titleId);

    int deleteOptionByTitleIds(List<TitleModel> titleModelList);

    int deleteOptionModelList(List<OptionModel> optionModelList);

    int updateOption(OptionModel optionModel);

    int updateOptionList(List<OptionModel> optionModelList);

    List<OptionModel> queryOptionListByTitleIds(List<SurveyTitleModel> surveyTitleModelList);

    List<SubmitOptionModel> querySubmitOptionList(@Param("optionId") String optionId,@Param("titleType") String titleType);


}
