package com.tubitak.fellas.sorumucozermisin.classes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tubitak.fellas.sorumucozermisin.QuestionActivity;
import com.tubitak.fellas.sorumucozermisin.R;

import java.util.List;

/**
 * Created by mehmet on 13/05/16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private Context context;
    private List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView questionText, questionTitle, questionUsername;
        public ImageView questionImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            questionImage = (ImageView) itemView.findViewById(R.id.questionImage);
            questionText = (TextView) itemView.findViewById(R.id.questionText);
            questionTitle = (TextView) itemView.findViewById(R.id.questionTitle);
            questionUsername = (TextView) itemView.findViewById(R.id.questionUsername);
        }
    }
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_row,parent,false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.MyViewHolder holder, final int position) {
        Question question = questionList.get(position);
        holder.questionTitle.setText(question.getTitle());
        holder.questionText.setText(question.getQuestion());
        holder.questionImage.setImageResource(R.mipmap.ic_launcher);
        holder.questionUsername.setText(question.getUsername()+". tarafından soruldu");
        holder.questionImage.setImageBitmap(question.getBitmapPhoto());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question selected = questionList.get(position);
                Log.i("aaabiii",questionList.get(position).getId()+" "+questionList.get(position).getQuestion());
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("idQuestion",selected.getId());
                intent.putExtra("username",selected.getUsername());
                intent.putExtra("title",selected.getTitle());
                intent.putExtra("question",selected.getQuestion());
                intent.putExtra("photo",selected.getPhoto());
                intent.putExtra("bitmap",selected.getBitmapPhoto());
                intent.putExtra("date",selected.getDate());
                context.startActivity(intent);
            }
        });
    }

    public void updateList(List<Question> newQuestions){
        questionList = newQuestions;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
