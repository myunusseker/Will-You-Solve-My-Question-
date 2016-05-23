package com.tubitak.fellas.sorumucozermisin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    Question myQ;
    private TextView title;
    private ImageView photo;
    private TextView question;
    private TextView username;
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        myQ = new Question(
                getIntent().getIntExtra("idQuestion",-1),
                getIntent().getStringExtra("username"),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("question"),
                getIntent().getStringExtra("photo"),
                getIntent().getStringExtra("date")
        );
        title = (TextView) findViewById(R.id.titleQuestion);
        photo = (ImageView) findViewById(R.id.photoQuestion);
        question = (TextView) findViewById(R.id.question);
        username = (TextView) findViewById(R.id.usernameQuestion);
        date = (TextView) findViewById(R.id.dateQuestion);
        title.setText(myQ.getTitle());
        question.setText(myQ.getQuestion());
        username.setText(myQ.getUsername() + "tarafindan soruldu");
        date.setText(myQ.getDate());
    }
}
