package com.sg.survey;

public enum SurveyType {

    Questionnaire("questionnaire"),KnowledgeCompetition("knowledge-competition");

    private String type;

    private SurveyType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
