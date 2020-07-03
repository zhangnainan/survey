package com.sg.survey.submit;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitSingleTitleDao {

    public int insertSubmitSingleTitle(SubmitOptionModel submitOptionModel);

    public int insertSubmitSingleTitleList(List<SubmitOptionModel> submitOptionModelList);

    public int deleteSubmitSingleTitleBySubmitId(String submitId);

    public List<SubmitSingleTitleModel> querySubmitSingleTitleListBySubmitId(String submitId);
}
