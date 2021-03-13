package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.SelectionActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    // Authentication
    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel.createInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();


    }

    public void toSelection(View v) {
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void toHome(View v) {
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

    public void toMyExperiments(View v){
        // TODO Bring the user from main menu, to experiment type menu
        Intent intent = new Intent(this, MyExperimentActivity.class);
        startActivity(intent);
    }

//    public void quickTest(View v) throws Exception {
//
//        DocumentReference user_reference = MainModel.getUserReference();
//
//        user_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                String user_name = value.get("user_name").toString();
//
//                Log.d("onEvent: ",user_name);
//
//
//            }
//        });
//
//    }

//    public void signIn(){
//
//        TextView userID = (TextView) findViewById(R.id.user_id);
//
//        userID.setText(MainModel.signInUser());
//    }

    private void updateUI(FirebaseUser user) {
        TextView user_id = findViewById(R.id.user_id);
        if (user != null) {
            user_id.setText("User ID: " + mAuth.getCurrentUser().getUid());
        } else {
            user_id.setText("User ID: " + mAuth.getCurrentUser());
        }
    }


    /**
     * Sign in the user anonymously
     */
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}