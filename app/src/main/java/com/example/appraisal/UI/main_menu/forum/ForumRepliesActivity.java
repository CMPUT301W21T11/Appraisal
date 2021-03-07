package com.example.appraisal.UI.main_menu.forum;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ForumRepliesActivity extends MainMenuCommonActivity {

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
        Collections.addAll(replies, experiments);
        reply_adapter = new ArrayAdapter<>(this, R.layout.list_content, replies);

        reply_list.setAdapter(reply_adapter);
    }
}
