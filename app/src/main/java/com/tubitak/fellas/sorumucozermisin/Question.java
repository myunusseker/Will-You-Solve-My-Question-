package com.tubitak.fellas.sorumucozermisin;

/**
 * Created by mehmet on 13/05/16.
 */
public class Question {
    private String imageLink, text, title, date;

    public Question(String imageLink, String text, String title, String date) {
        this.imageLink = imageLink;
        this.text = text;
        this.title = title;
        this.date = date;
    }

    public String getImageLink(){
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
