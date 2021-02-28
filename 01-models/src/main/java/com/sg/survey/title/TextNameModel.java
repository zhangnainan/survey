package com.sg.survey.title;


import java.text.Collator;
import java.util.Locale;

/**
 * Created by jiuge on 2020/6/29.
 */
public class TextNameModel implements Comparable<TextNameModel>{

    private String text;
    private String name;

    public TextNameModel() {

    }

    public TextNameModel(String text, String name){
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(TextNameModel textNameModel) {
        Collator instance = Collator.getInstance(Locale.CHINA);
        return instance.compare(this.name, textNameModel.getName());
    }
}
