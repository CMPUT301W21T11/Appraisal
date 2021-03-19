package com.example.appraisal.UI.main_menu.forum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ForumQuestionsActivity extends MainMenuCommonActivity {

    private ListView question_list;
    private ArrayList<String> questions;
    private ArrayAdapter<String> question_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_questions);

        question_list = findViewById(R.id.forum_questions);
        questions = new ArrayList<>();

        String []experiments = {"Question 1", "Question 2", "Question 3"};
        Collections.addAll(questions, experiments);

        Intent intent = new Intent(this, ForumRepliesActivity.class);

        question_adapter = new ArrayAdapter<>(this, R.layout.list_content, questions);
        question_list.setOnItemClickListener((parent, view, position, id) -> startActivity(intent));

        question_list.setAdapter(question_adapter);
    }
}
