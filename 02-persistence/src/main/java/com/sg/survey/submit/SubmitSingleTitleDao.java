package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitSingleTitleDao {

    public int insertSubmitSingleTitle(SubmitOptionModel submitOptionModel);

    public int insertSubmitSingleTitleList(List<SubmitOptionModel> submitOptionModelList);

    public int deleteSubmitSingleTitleBySubmitId(String submitId);

    public int deleteSubmitSingleByTitleId(List<TitleModel> titleModelList);

    public List<SubmitSingleTitleModel> querySubmitSingleTitleListBySubmitId(String submitId);
}
