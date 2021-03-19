package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;

import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.model.MainModel;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel.createInstance();
    }


    public void signIn(View v){
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

}