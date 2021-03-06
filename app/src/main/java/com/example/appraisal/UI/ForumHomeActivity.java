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
import java.util.Arrays;

public class ForumHomeActivity extends AppCompatActivity {

    private ListView forum_list;
    private ArrayList<String> forum_content;
    private ArrayAdapter<String> forum_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_home);

        forum_list = findViewById(R.id.forum_experiments);
        forum_content = new ArrayList<>();

        String []experiments = {"Experiment 1", "Experiment 2", "Experiment 3", "Experiment 4"};
        forum_content.addAll(Arrays.asList(experiments));

        Intent intent = new Intent(this, ForumQuestionsActivity.class);

        forum_adapter = new ArrayAdapter<>(this, R.layout.list_content, forum_content);

        forum_list.setOnItemClickListener((parent, view, position, id) -> {
            // open question activity with questions specific to experiment clicked
            // for now just opens template
            startActivity(intent);
        });

        forum_list.setAdapter(forum_adapter);

    }


    public void forumToHome(View v) {
        // TODO When click on home button
    }

    public void forumToSearch(View v) {
        // TODO When click on search button
    }

    public void forumToMyExps(View v) {
        // TODO When click on expList button
    }

    public void forumToProfile(View v) {
        // TODO When click on profile button
    }



}
