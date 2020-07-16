package com.sg.survey.title;

import com.sg.survey.Result;
import com.sg.survey.title.option.OptionModel;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleService {

    public Result getTextTitleModelList(String surveyId);

    public Result saveTitleModel(TitleModel<OptionModel> titleModel);

    public Result updateTitleModel(TitleModel<OptionModel> titleModel);

    public Result deleteTitleModel(TitleModel<OptionModel> titleModel);
}
