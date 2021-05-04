package com.sg.survey.title.answer;

public class AnswerModel {

    private String id;
    private String titleId;
    private String answer;
    private int answerSequence;

    public AnswerModel(){

    }

    public AnswerModel(String id, String titleId, String answer, int answerSequence){
        this.id = id;
        this.titleId = titleId;
        this.answer = answer;
        this.answerSequence = answerSequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerSequence() {
        return answerSequence;
    }

    public void setAnswerSequence(int answerSequence) {
        this.answerSequence = answerSequence;
    }
}
