package com.example.appraisal.UI.main_menu.user_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;

import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private TextView name_view;
    private TextView email_view;
    private TextView phone_view;

    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name_view = findViewById(R.id.name_textview);
        email_view = findViewById(R.id.email_address_textview);
        phone_view = findViewById(R.id.phone_number_textview);

        // TODO This part will need to be replace by data from the Firebase, not dummy data.
        if (getIntent().getExtras() == null) {
            User dummy = new User("dummyID1234", "John",
                    "abc@hotmail.com", "780 999 9999");

            setUserDisplay(dummy);
            current_user = dummy;
        }
        else {
            Bundle b = getIntent().getExtras();
            User sent_information = b.getParcelable("user");

            setUserDisplay(sent_information);
            current_user = sent_information;
        }
    }


    private void setUserDisplay(User u) {
        name_view.setText(u.getUsername());
        email_view.setText(u.getEmail());
        phone_view.setText(u.getPhoneNumber());
    }

    public void editUserProfile(View v) {

        Intent intent = new Intent(this, EditProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", current_user);

        intent.putExtras(bundle);
        startActivity(intent);
    }

}
