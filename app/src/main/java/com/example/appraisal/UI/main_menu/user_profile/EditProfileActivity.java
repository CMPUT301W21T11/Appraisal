package com.example.appraisal.UI.main_menu.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * This activity allows users to edit their profiles
 */
public class EditProfileActivity extends AppCompatActivity {

    private TextView id_view;
    private EditText name_edit;
    private EditText email_edit;
    private EditText phone_edit;
    private User current_user;
    private DocumentReference user_reference;

    /**
     * onCreate activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        id_view = (TextView) findViewById(R.id.id_textview);
        name_edit = (EditText) findViewById(R.id.name_edittext);
        email_edit = (EditText) findViewById(R.id.email_address_edittext);
        phone_edit = (EditText) findViewById(R.id.phone_number_edittext);

        current_user = getIntent().getExtras().getParcelable("user");

        id_view.setText("@" + current_user.getId().substring(0, 7));
        name_edit.setText(current_user.getUsername());
        email_edit.setText(current_user.getEmail());
        phone_edit.setText(current_user.getPhoneNumber());
    }

    /**
     * Gets called when user clicks the save button
     *
     * @param v -- view that is clicked
     * @throws Exception -- when the MainModel is not initiated
     */
    public void applyChangesToProfile(View v) throws Exception {

        User updated_user = new User(current_user.getId(),
                name_edit.getText().toString(),
                email_edit.getText().toString(),
                phone_edit.getText().toString()
        );

        MainModel.setCurrentUser(updated_user);

        // upload updated user information to database
        try {
            user_reference = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Author: Google
        // Reference: https://firebase.google.com/docs/firestore/manage-data/add-data

        user_reference
                .update("userName", name_edit.getText().toString(), "userEmail", email_edit.getText().toString(),
                        "phoneNumber", phone_edit.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("USER UPDATE", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("USER UPDATE", "Error updating document", e);
                    }
                });

        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("flag", "Main");
        finish();
    }

    /**
     * Gets called when user clicks the cancel button
     *
     * @param v -- view that is clicked
     */
    public void cancelChangesToProfile(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("flag", "Main");
        finish();
    }
}
