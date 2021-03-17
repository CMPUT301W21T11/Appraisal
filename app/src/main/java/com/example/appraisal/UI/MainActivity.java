package com.example.appraisal.UI;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appraisal.R;
import com.example.appraisal.model.MainModel;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void signIn(View v){
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