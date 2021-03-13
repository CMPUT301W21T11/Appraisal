package com.example.appraisal.UI.main_menu.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private DocumentReference user_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        id_view = findViewById(R.id.user_id_textview);
        name_view = findViewById(R.id.name_textview);
        email_view = findViewById(R.id.email_address_textview);
        phone_view = findViewById(R.id.phone_number_textview);

        try {
            user_reference = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String user_id = user_reference.get().toString();
                String user_name = value.get("user_name").toString();
                String user_email = value.get("user_email").toString();
                String phone_number = value.get("phone_number").toString();

                User user = new User(user_id, user_name, user_email, phone_number);

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
        id_view.setText("@"+u.getID().substring(0, 7));
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
