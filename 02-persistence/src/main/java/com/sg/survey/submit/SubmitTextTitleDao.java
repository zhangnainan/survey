package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

/**
 * Created by jiuge on 2020/5/18.
 */
public interface SubmitTextTitleDao {

    public int insertSubmitTextTitle(SubmitTextTitleModel submitTextTitleModel);

    public int insertSubmitTextTitleList(List<SubmitTextTitleModel> submitTextTitleModelList);

    public int deleteSubmitTextTitleBySubmitId(String submitId);

    public int deleteSubmitTextByTitleId(List<TitleModel> titleModelList);

    public List<SubmitTextTitleModel> querySubmitTextTitleListBySubmitId(String submitId);

}
