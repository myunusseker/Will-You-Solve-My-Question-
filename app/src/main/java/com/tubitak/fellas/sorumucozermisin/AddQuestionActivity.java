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
import android.widget.TextView;
import android.widget.Toast;

import com.tubitak.fellas.sorumucozermisin.classes.Globals;
import com.tubitak.fellas.sorumucozermisin.classes.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddQuestionActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private EditText title, question;
    private TextView textView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String titleStr, questionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        title = (EditText) findViewById(R.id.questionTitle);
        question = (EditText) findViewById(R.id.questionText);
        textView = (TextView) findViewById(R.id.textInfoPhoto);
        imageView = (ImageView) findViewById(R.id.newQuestionImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
            textView.setVisibility(View.INVISIBLE);
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setBackground(null);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private class SendQuestion extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String imageString = "";
            if(imageBitmap != null)
                imageString = RequestHandler.getStringImage(imageBitmap);
            HashMap<String, String> data = new HashMap<>();
            data.put("username", Globals.username);
            data.put("title",titleStr);
            data.put("question",questionStr);
            data.put("photo",imageString);
            Log.i("asdf", "-> " + imageString);
            String response = RequestHandler.sendPostRequest(Globals.url+"addQuestion.php",data);
            Log.i("response",response);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject json = new JSONObject(response);
                if(json.getString("result").equals("success")){
                    Intent intent = new Intent(AddQuestionActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.i("asdf","response->" + response);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("asdf","response2 -> Database problemi" + response);
            }
        }
    }
}