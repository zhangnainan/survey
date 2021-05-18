package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitSingleTitleDao {

    int insertSubmitSingleTitle(SubmitOptionModel submitOptionModel);

    int insertSubmitSingleTitleList(List<SubmitOptionModel> submitOptionModelList);

    int deleteSubmitSingleTitleBySubmitId(String submitId);

    int deleteSubmitSingleByTitleId(List<TitleModel> titleModelList);

    List<SubmitSingleTitleModel> querySubmitSingleTitleListBySubmitId(String submitId);
}
