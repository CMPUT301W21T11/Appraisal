package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.subscription.ExpSubscriptionActivity;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button begin_btn;
    private View loading_panel;
    private Animation begin_btn_animation;
    private ImageView title_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();

//        getWindow().setDecorFitsSystemWindows(false);
//        WindowInsetsController controller = getWindow().getInsetsController();
//        if (controller != null) {
//            controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
//            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
//        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_main);

        begin_btn = (Button) findViewById(R.id.begin_button);
        begin_btn.setVisibility(View.INVISIBLE);
        loading_panel = (View) findViewById(R.id.loadingPanel);
        begin_btn_animation = AnimationUtils.loadAnimation(this, R.anim.begin_btn_fade_in_animation);

        title_display = findViewById(R.id.title_display);
        title_display.setVisibility(View.INVISIBLE);
        title_display.startAnimation(begin_btn_animation);
        title_display.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "USER SIGNED IN SUCCESSFULLY");
                    try {
                        MainModel.checkUserStatus();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loading_panel.setVisibility(View.GONE);
                    begin_btn.startAnimation(begin_btn_animation);
                    begin_btn.setVisibility(View.VISIBLE);
                }
            }
        });

        MainModel.createInstance();
    }

    public void signIn(View v){
        Intent intent = new Intent(this, ExpSubscriptionActivity.class);
        startActivity(intent);
    }

}