package com.sg.survey.submit;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitMultipleTitleDao {

    public int insertSubmitMultipleTitle(SubmitMultipleTitleModel submitMultipleTitleModel);

    public int insertSubmitMultipleTitleList(List<SubmitOptionModel> submitOptionModelList);

    public int deleteSubmitBySubmitId(String submitId);

    public List<SubmitMultipleTitleModel> querySubmitMultipleTitleListBySubmitId(String submitId);

}
