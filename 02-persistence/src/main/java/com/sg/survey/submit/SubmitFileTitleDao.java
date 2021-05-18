package com.sg.survey.submit;

import com.sg.survey.title.TitleModel;

import java.util.List;

public interface SubmitFileTitleDao {

    int insertSubmitFileTitle(SubmitFileTitleModel submitFileTitleModel);

    int insertSubmitFileTitleList(List<SubmitFileTitleModel> submitFileTitleModelList);

    int deleteSubmitFileTitleBySubmitId(String submitId);

    int deleteSubmitFileByTitleId(List<TitleModel> titleModelList);

    List<SubmitFileTitleModel> querySubmitFileTitleListBySubmitId(String submitId);

}
