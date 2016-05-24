package com.tubitak.fellas.sorumucozermisin.classes;

/**
 * Created by BIGMAC on 24.05.2016.
 */
public class Answer {
    private int id;
    private String username,answer,date;
    public Answer(int id,String username,String answer, String date) {
        setId(id);
        setUsername(username);
        setAnswer(answer);
        setDate(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
