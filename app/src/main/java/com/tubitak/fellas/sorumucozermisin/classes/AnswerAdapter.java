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

import com.tubitak.fellas.sorumucozermisin.R;

import java.util.List;

/**
 * Created by BIGMAC on 24.05.2016.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    private Context context;
    private List<Answer> answerList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView answerText, answerUsername, answerDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            answerText = (TextView) itemView.findViewById(R.id.answer);
            answerUsername = (TextView) itemView.findViewById(R.id.answerUsername);
            answerDate = (TextView) itemView.findViewById(R.id.answerDate);
        }
    }

    public AnswerAdapter(List<Answer> answerList) {
        this.answerList = answerList;
    }
    @Override

    public AnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_list_row,parent,false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.MyViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.answerText.setText(answer.getAnswer());
        holder.answerDate.setText(answer.getDate());
        holder.answerUsername.setText(answer.getUsername()+" tarafından cevaplandı");
    }
    public void updateList(List<Answer> newAnswers){
        answerList = newAnswers;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return answerList.size();
    }
}
