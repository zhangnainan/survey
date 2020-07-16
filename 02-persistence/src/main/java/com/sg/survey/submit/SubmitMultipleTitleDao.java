package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitMultipleTitleDao {

    public int insertSubmitMultipleTitle(SubmitMultipleTitleModel submitMultipleTitleModel);

    public int insertSubmitMultipleTitleList(List<SubmitOptionModel> submitOptionModelList);

    public int deleteSubmitBySubmitId(String submitId);

    public int deleteSubmitMultipleByTitleId(List<TitleModel> titleModelList);

    public List<SubmitMultipleTitleModel> querySubmitMultipleTitleListBySubmitId(String submitId);

}
