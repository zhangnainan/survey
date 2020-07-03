package com.sg.survey.title;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiuge on 2020/6/26.
 */
public interface TitleDao {

    public List<TitleModel> getTitleModelList(@Param("surveyId") String surveyId,@Param("titleType") String titleType);

}
