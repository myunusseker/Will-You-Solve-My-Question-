package com.tubitak.fellas.sorumucozermisin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    private Button button ;
    private EditText username;
    private EditText password;
    private TextView signup;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref",Context.MODE_PRIVATE);

        String user = sharedPref.getString(getString(R.string.username_string),"YOKKK");
        Log.i("asdf",user);
        if(!user.equals("YOKKK")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.login_button);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signup_text);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , SignupActivity.class);
                startActivity(intent);
            }
        });
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LoginActivity.this.getCurrentFocus();
                if(view!=null)
                {
                    InputMethodManager mng = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mng.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
                String user = username.getText().toString(),
                        pass = password.getText().toString(),
                        errorMessage = "";

                if (user.length() == 0)
                    errorMessage += "Username is empty\n";
                if(password.length() == 0)
                    errorMessage += "Password field is empty\n";

                if(errorMessage.equals(""))
                    new Async().execute(username.getText().toString(),password.getText().toString());
                else
                    Snackbar.make(coordinatorLayout,errorMessage,Snackbar.LENGTH_LONG)
                        .show();
            }
        });
     }

    public class Async extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://188.166.167.178/login.php";
            String username = params[0];
            String password = params[1];
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username",username)
                    .add("password",password)
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
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("name",reader.getString("name"));
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout,"Kullanici adi veya sifre hatali !",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

