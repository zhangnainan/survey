package com.sg.survey.title;

import com.sg.survey.title.option.OptionModel;

/**
 * Created by jiuge on 2020/6/12.
 */
public class TitleInfoModel extends TitleModel<OptionModel> {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
