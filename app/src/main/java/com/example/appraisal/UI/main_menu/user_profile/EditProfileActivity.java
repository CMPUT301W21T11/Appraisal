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
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class EditProfileActivity extends AppCompatActivity {

    private TextView id_view;
    private EditText name_edit;
    private EditText email_edit;
    private EditText phone_edit;
    private User current_user;
    private DocumentReference user_reference;
    private View view;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        save_button = (Button)findViewById(R.id.apply_changes_btn);

        id_view = (TextView)findViewById(R.id.id_textview);
        name_edit = (EditText)findViewById(R.id.name_edittext);
        email_edit = (EditText)findViewById(R.id.email_address_edittext);
        phone_edit = (EditText)findViewById(R.id.phone_number_edittext);

        current_user = getIntent().getExtras().getParcelable("user");

        id_view.setText("@"+current_user.getID().substring(0, 7));
        name_edit.setText(current_user.getUsername());
        email_edit.setText(current_user.getEmail());
        phone_edit.setText(current_user.getPhoneNumber());
    }

    public void applyChangesToProfile(View v) throws Exception {

        String email = email_edit.getText().toString();
        String phone = phone_edit.getText().toString();

        User updated_user = new User(current_user.getID(),
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

        user_reference
                .update("user_name", name_edit.getText().toString(), "user_email", email_edit.getText().toString(),
                            "phone_number", phone_edit.getText().toString())
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
        startActivity(intent);
    }

    public void cancelChangesToProfile(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
