package com.sg.survey.submit;

import java.util.List;

/**
 * Created by jiuge on 2020/5/20.
 */
public class SubmitMultipleTitleVo extends SubmitTitleVo {

    private List<String> answer;

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }
}
