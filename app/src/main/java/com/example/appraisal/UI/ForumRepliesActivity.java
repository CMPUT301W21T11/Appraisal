package com.example.appraisal.UI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;

import java.util.ArrayList;

public class ForumRepliesActivity extends AppCompatActivity {

    private ListView reply_list;
    private ArrayList<String> replies;
    private ArrayAdapter<String> reply_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_replies);

        reply_list = findViewById(R.id.forum_replies);
        replies = new ArrayList<>();

        String []experiments = {"Reply 1", "Reply 2", "Reply 3", "Reply 4"};
        for(int i = 0; i <experiments.length; i++){
            replies.add(experiments[i]);
        }

        reply_adapter = new ArrayAdapter<>(this, R.layout.list_content, replies);
        reply_list.setAdapter(reply_adapter);
    }
}
