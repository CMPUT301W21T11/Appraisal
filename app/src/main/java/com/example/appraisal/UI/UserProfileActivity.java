package com.example.appraisal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appraisal.R;

public class UserProfileActivity extends AppCompatActivity {

    private TextView name_view;
    private TextView email_view;
    private TextView phone_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name_view = findViewById(R.id.name_textview);
        email_view = findViewById(R.id.email_address_textview);
        phone_view = findViewById(R.id.phone_number_textview);
    }


    public void editUserProfile(View v) {

        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("profile", new String[]{name_view.getText().toString(), email_view.getText().toString(), phone_view.getText().toString()});

        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle updated_profile = data.getExtras();
                String[] updated_profile_array = updated_profile.getStringArray("updated profile");

                name_view.setText(updated_profile_array[0]);
                email_view.setText(updated_profile_array[1]);
                phone_view.setText(updated_profile_array[2]);
            }
        }
    }
}