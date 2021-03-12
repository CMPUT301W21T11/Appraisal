package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.SelectionActivity;
import com.example.appraisal.UI.main_menu.my_experiment.MyExperimentActivity;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.backend.user.FirebaseAuthentication;
import com.example.appraisal.model.MainModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import static com.example.appraisal.model.MainModel.getUserReference;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuthentication auth = new FirebaseAuthentication();
    public static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel.createInstance();

        MainModel.checkUserStatus();

        // Check if user is signed in (non-null) and update UI accordingly.
//        if (auth.isLoggedIn()){
//            user_id = auth.get_userID();
//
//            // Get their information
//        } else {
//            auth.sign_in();
//            user_id = auth.get_userID();
//
//            // Push the ID into firebase
//
//        }

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

    public void quickTest(View v) throws Exception {

        DocumentReference user_reference = MainModel.getUserReference();

        user_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String user_name = value.get("user_name").toString();

                Log.d("onEvent: ",user_name);


            }
        });

    }

    public void signOut(View v){

        TextView userID = (TextView) findViewById(R.id.user_id);

        MainModel.signOutUser();

        userID.setText(null);

    }

    public void signIn(View v){

        TextView userID = (TextView) findViewById(R.id.user_id);

        userID.setText(MainModel.signInUser());
    }
}