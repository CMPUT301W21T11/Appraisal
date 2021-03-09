package com.example.appraisal.UI.main_menu.user_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;

public class EditProfileActivity extends AppCompatActivity {


    private EditText name_edit;
    private EditText email_edit;
    private EditText phone_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name_edit = (EditText)findViewById(R.id.name_edittext);
        email_edit = (EditText)findViewById(R.id.email_address_edittext);
        phone_edit = (EditText)findViewById(R.id.phone_number_edittext);

        Bundle profile_bundle = getIntent().getExtras();
        String[] profile_array = profile_bundle.getStringArray("profile");

        name_edit.setText(profile_array[0]);
        email_edit.setText(profile_array[1]);
        phone_edit.setText(profile_array[2]);
    }

    public void applyChangesToProfile(View v) {

        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("updated profile",
                new String[] {name_edit.getText().toString(),
                        email_edit.getText().toString(),
                        phone_edit.getText().toString()});

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void cancelChangesToProfile(View v) {

        Intent intent = new Intent(this, UserProfileActivity.class);

        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
