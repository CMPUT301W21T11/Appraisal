package com.example.appraisal.UI.main_menu.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfileActivity extends AppCompatActivity {
    private TextView name_view;
    private TextView email_view;
    private TextView phone_view;
    private TextView id_view;
    private Button edit_button;

    private DocumentReference user_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        id_view = findViewById(R.id.user_id_textview);
        name_view = findViewById(R.id.name_textview);
        email_view = findViewById(R.id.email_address_textview);
        phone_view = findViewById(R.id.phone_number_textview);
        edit_button = findViewById(R.id.edit_profile_btn);

//        Intent intent = getIntent();
//        String name = intent.getStringExtra("experimenter");
//        int position = intent.getE("position");
//
//        String current_user = null;
//        try {
//            current_user = MainModel.getCurrentUser().getUsername();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (name.equals(current_user)){
//            // if current user, get reference to current user profile
//            try {
//                user_reference = MainModel.getUserReference();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // make edit_button visible
//            edit_button.setVisibility(View.VISIBLE);
//            getUserInfo(user_reference);
//        } else {
//            // if other user, get reference to that user
//            try {
//                user_reference = MainModel.retrieveSpecificUser(name);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // make edit_button invisible
//            edit_button.setVisibility(View.INVISIBLE);
//            getUserInfo(user_reference);
//        }
//
        try {
            user_reference = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getUserInfo(user_reference);

    }

    private void getUserInfo(DocumentReference user_reference) {
        user_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String user_id = user_reference.get().toString();
                String user_name = value.get("user_name").toString();
                String user_email = value.get("user_email").toString();
                String phone_number = value.get("phone_number").toString();
                Integer num_of_exp = Integer.valueOf(value.get("num_of_my_exp").toString());

                User user = new User(user_id, user_name, user_email, phone_number);
                user.setNum_of_exp(num_of_exp);

                try {
                    MainModel.setCurrentUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                setUserDisplay(user);
            }
        });
    }


    private void setUserDisplay(User u) {
        id_view.setText("@" + u.getID().substring(0, 7));
        name_view.setText(u.getUsername());
        email_view.setText(u.getEmail());
        phone_view.setText(u.getPhoneNumber());
    }

    public void editUserProfile(View v) throws Exception {

        Intent intent = new Intent(this, EditProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", MainModel.getCurrentUser());

        intent.putExtras(bundle);
        startActivity(intent);
    }

}
