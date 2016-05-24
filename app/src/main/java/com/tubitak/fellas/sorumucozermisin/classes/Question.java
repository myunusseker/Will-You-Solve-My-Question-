package com.tubitak.fellas.sorumucozermisin.classes;

import android.graphics.Bitmap;

public class Question {
    private int id;
    private String username, photo, question, title, date;
    private Bitmap bitmapPhoto;

    public Question(int id,String username,String title, String question, String date) {
        setId(id);
        setUsername(username);
        setTitle(title);
        setQuestion(question);
        setDate(date);
    }
    public Question(int id,String username,String title, String question, Bitmap bitmapPhoto,String noPhoto, String date) {
        setId(id);
        setUsername(username);
        setTitle(title);
        setQuestion(question);
        setBitmapPhoto(bitmapPhoto);
        setDate(date);
        setPhoto(noPhoto);
    }

    public Bitmap getBitmapPhoto() {
        return bitmapPhoto;
    }

    public void setBitmapPhoto(Bitmap bitmapPhoto) {
        this.bitmapPhoto = bitmapPhoto;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
