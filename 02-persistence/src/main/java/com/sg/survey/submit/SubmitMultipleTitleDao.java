package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitMultipleTitleDao {

    int insertSubmitMultipleTitle(SubmitMultipleTitleModel submitMultipleTitleModel);

    int insertSubmitMultipleTitleList(List<SubmitOptionModel> submitOptionModelList);

    //public int deleteSubmitBySubmitId(String submitId);
    int deleteSubmitMultipleTitleBySubmitId(String submitId);

    int deleteSubmitMultipleByTitleId(List<TitleModel> titleModelList);

    List<SubmitMultipleTitleModel> querySubmitMultipleTitleListBySubmitId(String submitId);

    List<SubmitMultipleTitleModel> querySubmitMultipleTitleModelGroupByTitleId(String submitId);
}
