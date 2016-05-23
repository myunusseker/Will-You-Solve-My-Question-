package com.tubitak.fellas.sorumucozermisin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tubitak.fellas.sorumucozermisin.classes.Globals;
import com.tubitak.fellas.sorumucozermisin.classes.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddQuestionActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private EditText title, question;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String titleStr, questionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        title = (EditText) findViewById(R.id.questionTitle);
        question = (EditText) findViewById(R.id.questionText);
        imageView = (ImageView) findViewById(R.id.newQuestionImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        });

        button = (Button) findViewById(R.id.askQuestion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = title.getText().toString();
                questionStr = question.getText().toString();
                new SendQuestion().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private class SendQuestion extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String imageString = RequestHandler.getStringImage(imageBitmap);
            HashMap<String, String> data = new HashMap<>();
            data.put("username", Globals.username);
            data.put("title",titleStr);
            data.put("question",questionStr);
            data.put("photo",imageString);

            String Datetime;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            Datetime = dateformat.format(c.getTime());
            data.put("date",Datetime);
            String response = RequestHandler.sendPostRequest(Globals.url+"addQuestion.php",data);
            Log.i("response",response);
            return response;
        }
    }
}