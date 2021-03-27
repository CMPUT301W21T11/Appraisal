package com.example.appraisal.UI.main_menu.forum;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.MainActivity;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.specific_experiment_details.SpecificExpDiscussionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ForumRepliesActivity extends  AppCompatActivity {

    private ListView reply_list;
    private ArrayList<String> replies;
    private ArrayAdapter<String> reply_adapter;
    private EditText reply_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_replies);

        String question = getIntent().getStringExtra("Question");

        TextView textView = findViewById(R.id.question);
        textView.setText(question);

        reply_list = findViewById(R.id.forum_replies);
        replies = new ArrayList<>();

        String []experiments = {"Reply 1", "Reply 2", "Reply 3", "Reply 4"};
        Collections.addAll(replies, experiments);
        reply_adapter = new ArrayAdapter<>(this, R.layout.list_content, replies);

        reply_list.setAdapter(reply_adapter);

        //posting a new reply
        final Button publishNewReply = findViewById(R.id.reply_button);
        publishNewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(ForumRepliesActivity.this);
                builder.setTitle("Post a reply!");

                reply_input = new EditText(ForumRepliesActivity.this);
                builder.setView(reply_input);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = reply_input.getText().toString();
                        replies.add(text);
                        reply_adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog reply_box = builder.create();
                reply_box.show();
            }
        });

    }
}
