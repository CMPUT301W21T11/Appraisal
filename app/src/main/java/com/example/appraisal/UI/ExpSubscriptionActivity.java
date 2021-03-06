package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.UI.ForumHomeActivity;
import com.example.appraisal.UI.MyExperimentActivity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExpSubscriptionActivity extends AppCompatActivity {

    // TODO
    private ListView subscribed_list;
    private List<String> subscribed_content;
    private ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        // Testing dummy data
        String[] dummy = {"a", "b", "c"};
        subscribed_content = Arrays.stream(dummy).collect(Collectors.toList());

        subscribed_list = findViewById(R.id.subscribedList);
        adapter = new ArrayAdapter<>(this, R.layout.list_content, subscribed_content);
        subscribed_list.setAdapter(adapter);
    }

    public void toHome(View v) {
        // TODO When click on home button
    }

    public void toSearch(View v) {
        // TODO When click on search button
    }

    public void toForum(View v) {
        // When click on forum button
        Intent intent = new Intent(this, ForumHomeActivity.class);
        startActivity(intent);
    }

    public void toMyExps(View v) {
        // When click on expList button
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }

    public void toProfile(View v) {
        // TODO When click on profile button
    }
}
