package com.example.appraisal.UI.main_menu.user_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;

public class EditProfileActivity extends AppCompatActivity {


    private EditText name_edit;
    private EditText email_edit;
    private EditText phone_edit;
    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name_edit = (EditText)findViewById(R.id.name_edittext);
        email_edit = (EditText)findViewById(R.id.email_address_edittext);
        phone_edit = (EditText)findViewById(R.id.phone_number_edittext);

        current_user = getIntent().getExtras().getParcelable("user");

        name_edit.setText(current_user.getUsername());
        email_edit.setText(current_user.getEmail());
        phone_edit.setText(current_user.getPhoneNumber());
    }

    public void applyChangesToProfile(View v) {

        User new_user = new User(current_user.getID(),
                name_edit.getText().toString(),
                email_edit.getText().toString(),
                phone_edit.getText().toString()
        );

        Intent intent = new Intent(this, UserProfileActivity.class);Bundle bundle = new Bundle();

        bundle.putParcelable("user", new_user);
        // setResult(Activity.RESULT_OK, intent);
        intent.putExtras(bundle);

        // finish();
        startActivity(intent);
    }

    public void cancelChangesToProfile(View v) {

        Intent intent = new Intent(this, UserProfileActivity.class);

        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
