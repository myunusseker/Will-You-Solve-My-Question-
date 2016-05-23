package com.tubitak.fellas.sorumucozermisin;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by mehmet on 13/05/16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

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

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.MyViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.questionTitle.setText(question.getTitle());
        holder.questionText.setText(question.getQuestion());
        holder.questionImage.setImageResource(R.mipmap.ic_launcher);
        holder.questionUsername.setText(question.getUsername()+" tarafından soruldu");
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
