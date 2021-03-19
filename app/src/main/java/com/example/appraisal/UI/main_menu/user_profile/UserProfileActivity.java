package com.example.appraisal.UI.main_menu.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

//        // get current user
//        String current_user = null;
//        try {
//            current_user = MainModel.getCurrentUser().getUsername();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Get intent, and flag to know which activity it came from
        String name;
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");

        // if came from Specific Activity, to view Other User
        if (flag.equals("Other")){
            name = intent.getStringExtra("experimenter");
            int position = intent.getIntExtra("position", -1);
            Log.d("User- name:", name);
            Log.d("User-position:", String.valueOf(position));
            showOtherUser(name);
        }
        else {   // else came from main activity, need to display current user
            Log.d("user:", "from main");
            showCurrentUser();
        }
    }

    /**
     * Show the details of the user that has been clicked on
     * @param name
     */
    private void showOtherUser(String name) {
        try {
            user_reference = MainModel.retrieveSpecificUser(name);
            Log.d("IDOther:", String.valueOf(user_reference));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // make edit_button invisible
        edit_button.setVisibility(View.INVISIBLE);
//        id_view.setVisibility(View.INVISIBLE);
        getUserInfo(user_reference, false);
    }

    /**
     * Show the details of the current User.
     */
    private void showCurrentUser() {
        try {
            user_reference = MainModel.getUserReference();
            Log.d("IDMain:", String.valueOf(user_reference));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // make edit_button visible
        edit_button.setVisibility(View.VISIBLE);
        getUserInfo(user_reference, true);
    }

    /**
     * Queries the database depending on which user profile needs to be displayed
     * @param user_reference
     * @param isMain
     */
    private void getUserInfo(DocumentReference user_reference, Boolean isMain) {
        user_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String user_id = value.getId();
                Log.d("value.id:" , user_id);
                String user_name = value.get("user_name").toString();
                String user_email = value.get("user_email").toString();
                String phone_number = value.get("phone_number").toString();
                Integer num_of_exp = Integer.valueOf(value.get("num_of_my_exp").toString());

                User user = new User(user_id, user_name, user_email, phone_number);
                user.setNum_of_exp(num_of_exp);


                if(isMain) {
                    try {
                        MainModel.setCurrentUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                setUserDisplay(user);
            }
        });
    }

    /**
     * Set the TextFields with the user's info
     * @param u
     */
    private void setUserDisplay(User u) {
        id_view.setText("@" + u.getID().substring(0, 7));
        name_view.setText(u.getUsername());
        email_view.setText(u.getEmail());
        phone_view.setText(u.getPhoneNumber());
    }

    /**
     * Called when Edit Button is clicked, go to EditUserProfile Activity
     * @param v
     * @throws Exception
     */
    public void editUserProfile(View v) throws Exception {

        Intent intent = new Intent(this, EditProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", MainModel.getCurrentUser());

        intent.putExtras(bundle);
        startActivity(intent);
    }

}
