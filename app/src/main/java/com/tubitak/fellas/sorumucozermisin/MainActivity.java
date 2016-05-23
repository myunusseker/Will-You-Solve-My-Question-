package com.tubitak.fellas.sorumucozermisin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Question> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddQuestion.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        String name = sharedPref.getString("name","Name yok.!");
        Toast.makeText(this ,"Hosgeldin " + name , Toast.LENGTH_LONG).show();
        Globals.name = name;
        Globals.username = sharedPref.getString("username","asdf");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialize();
            }
        });
        mAdapter = new QuestionAdapter(questions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        initialize();
    }

    private void initialize() {
        new questionListAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.logout){
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public class questionListAsync extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            String url = "http://188.166.167.178/getQuestions.php";
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            Response response = null;
            try
            {
                response = call.execute();
                if (response.isSuccessful())
                {
                    result = response.body().string();
                }
                else
                {
                    result = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String questions) {
            super.onPostExecute(questions);
            List<Question> questionList= new Vector<Question>();
            JSONArray reader = null;
            if(questions!=null) {
                try {
                    reader = new JSONArray(questions);
                    for(int i=0;i<reader.length();i++)
                    {
                        JSONObject r = reader.getJSONObject(i);
                        questionList.add(new Question(r.getInt("idquestion"),
                                                    r.getString("username"),
                                                    r.getString("title"),
                                                    r.getString("question"),
                                                    r.getString("photo"),
                                                    r.getString("datequestion")
                                ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mAdapter.updateList(questionList);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
