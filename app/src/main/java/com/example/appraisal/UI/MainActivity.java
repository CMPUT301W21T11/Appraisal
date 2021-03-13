package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {


    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainModel.createInstance();
    }


    public void signIn(View v){

        id =  MainModel.signInUser();
        Intent begin_intent = new Intent(this, BeginActivity.class);
        startActivity(begin_intent);
    }









//    public void toSelection(View v) {
//        // TODO Bring the user from main menu, to experiment type menu
//        Intent intent = new Intent(this, SelectionActivity.class);
//        startActivity(intent);
//    }
//
//    public void toHome(View v) {
//        // TODO Bring the user from main menu, to experiment type menu
//        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
//        startActivity(intent);
//    }
//
//    public void toMyExperiments(View v){
//        // TODO Bring the user from main menu, to experiment type menu
//        Intent intent = new Intent(this, MyExperimentActivity.class);
//        startActivity(intent);
//    }

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



}