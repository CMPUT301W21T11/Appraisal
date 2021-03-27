package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.forum.ForumRepliesActivity;
import com.example.appraisal.UI.main_menu.my_experiment.PublishExpActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class SpecificExpDiscussionFragment extends Fragment {

    private ListView question_list;
    private ArrayList<String> questions;
    private ArrayAdapter<String> question_adapter;
    private EditText question_input;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specific_exp_discussion, container, false);

        question_list = v.findViewById(R.id.forum_questions);
        questions = new ArrayList<>();

        String[] experiments = {"Question 1", "Question 2", "Question 3"};
        Collections.addAll(questions, experiments);

        question_adapter = new ArrayAdapter<>(this.getActivity(), R.layout.list_content, questions);
        question_list.setAdapter(question_adapter);

        //Intent intent = new Intent(this.getContext(), ForumRepliesActivity.class);
        // question_list.setOnItemClickListener((parent, view, position, id) -> startActivity(intent));

        question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int selected_question = position;
                String question_string = questions.get(selected_question);

                Intent intent = new Intent(getContext(), ForumRepliesActivity.class);
                intent.putExtra("Question", question_string);

                startActivity(intent);
            }
        });


        //posting a new question
        final FloatingActionButton publishNewQuestion = v.findViewById(R.id.AddQuestionButton);
        publishNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                builder.setTitle("Post a question!");

                question_input = new EditText(getContext());
                builder.setView(question_input);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = question_input.getText().toString();
                        // Collections.addAll(questions,text);
                        questions.add(text);
                        question_adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog question_box = builder.create();
                question_box.show();
            }
        });

        return v;
    }




}