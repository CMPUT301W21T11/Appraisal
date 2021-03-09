package com.example.appraisal.UI.main_menu.user_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;

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

        if (getIntent().getExtras().getParcelable("user") == null) {
            User dummy = new User("dummyID1234", "John",
                    "abc@hotmail.com", "780 999 9999");

            setUserDisplay(dummy);
            return;
        }
        else {
            Bundle b = getIntent().getExtras();
            User sent_information = b.getParcelable("user");

            setUserDisplay(sent_information);
            return;
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
        bundle.putParcelable("user", new User("dummyID1234",
                name_view.getText().toString(),
                email_view.getText().toString(),
                phone_view.getText().toString())
        );

        intent.putExtras(bundle);

        // intent.putExtra("profile", new String[] {name_view.getText().toString(),
        //         email_view.getText().toString(),
        //         phone_view.getText().toString()});

        // startActivityForResult(intent, 1);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle updated_profile = data.getExtras();
            String[] updated_profile_array =
                    updated_profile.getStringArray("updated profile");

            name_view.setText(updated_profile_array[0]);
            email_view.setText(updated_profile_array[1]);
            phone_view.setText(updated_profile_array[2]);
        }
    }

}
