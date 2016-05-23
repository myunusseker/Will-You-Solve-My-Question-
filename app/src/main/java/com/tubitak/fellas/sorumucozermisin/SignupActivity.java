package com.tubitak.fellas.sorumucozermisin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    private Button button ;
    private EditText username;
    private EditText name;
    private EditText password;
    private EditText passwordConfirm;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        button = (Button) findViewById(R.id.signup_button);
        username = (EditText) findViewById(R.id.usernameSignup);
        password = (EditText) findViewById(R.id.passwordSignup);
        name = (EditText) findViewById(R.id.nameSignup);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutSignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errormsg = "";
                View view = SignupActivity.this.getCurrentFocus();
                if(view!=null)
                {
                    InputMethodManager mng = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mng.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
                if(name.getText().toString().length() == 0)
                {
                    errormsg +="Lutfen Ad Soyad giriniz\n";
                }
                if(username.getText().toString().length() < 6)
                {
                    errormsg += "Kullanıcı adı en az 6 karakter olmalı.\n";
                }
                if(password.getText().toString().length() < 6)
                {
                    errormsg += "Şifre en az 6 karakter olmalı.\n";
                }
                if(!password.getText().toString().equals(passwordConfirm.getText().toString()))
                {
                    errormsg += "Şifreler aynı değil. Lütfen tekrar giriniz.\n";
                }
                if(errormsg.equals(""))
                    new signupAsync().execute(username.getText().toString(),password.getText().toString(),name.getText().toString())
                    ;
                else
                    Snackbar.make(coordinatorLayout,errormsg,Snackbar.LENGTH_LONG)
                            .show();
            }
        });
    }
    public class signupAsync extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://188.166.167.178/signup.php";
            String username = params[0];
            String password = params[1];
            String name = params[2];
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("name", name)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            Response response = null;
            String jsonData = null;
            try {
                response = call.execute();
                if (response.isSuccessful()) {
                    jsonData = response.body().string();
                } else {
                    jsonData = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject reader = null;

            try {
                reader = new JSONObject(s);
                String result = reader.getString("result");
                if(result.equals("success"))
                {
                    Intent intent = new Intent(SignupActivity.this , LoginActivity.class);
                    intent.putExtra("SignUp","success");
                    startActivity(intent);
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,"Kayit Basarisiz.",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
