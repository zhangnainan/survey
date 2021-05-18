package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitTextTitleDao {

    int insertSubmitTextTitle(SubmitTextTitleModel submitTextTitleModel);

    int insertSubmitTextTitleList(List<SubmitTextTitleModel> submitTextTitleModelList);

    int deleteSubmitTextTitleBySubmitId(String submitId);

    int deleteSubmitTextByTitleId(List<TitleModel> titleModelList);

    List<SubmitTextTitleModel> querySubmitTextTitleListBySubmitId(String submitId);

}
