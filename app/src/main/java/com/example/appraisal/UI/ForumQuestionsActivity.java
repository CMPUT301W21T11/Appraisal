package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;

import java.util.ArrayList;

public class ForumQuestionsActivity extends AppCompatActivity {

    private ListView question_list;
    private ArrayList<String> questions;
    private ArrayAdapter<String> question_adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_questions);

        question_list = findViewById(R.id.forum_questions);
        questions = new ArrayList<>();

        String []experiments = {"Question 1", "Question 2", "Question 3"};
        for(int i = 0; i <experiments.length; i++){
            questions.add(experiments[i]);
        }

        intent = new Intent(this, ForumRepliesActivity.class);

        question_adapter = new ArrayAdapter<>(this, R.layout.list_content, questions);
        question_list.setOnItemClickListener(question_listener);
        question_list.setAdapter(question_adapter);
    }

    private AdapterView.OnItemClickListener question_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // open replies activity with replies specific to question clicked
            // for now just opens template
            startActivity(intent);
        }
    };
}
