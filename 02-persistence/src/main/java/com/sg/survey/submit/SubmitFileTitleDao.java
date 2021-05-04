package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

public interface SubmitFileTitleDao {
    public int insertSubmitFileTitle(SubmitFileTitleModel submitFileTitleModel);

    public int insertSubmitFileTitleList(List<SubmitFileTitleModel> submitFileTitleModelList);

    public int deleteSubmitFileTitleBySubmitId(String submitId);

    public int deleteSubmitFileByTitleId(List<TitleModel> titleModelList);

    public List<SubmitFileTitleModel> querySubmitFileTitleListBySubmitId(String submitId);
}
