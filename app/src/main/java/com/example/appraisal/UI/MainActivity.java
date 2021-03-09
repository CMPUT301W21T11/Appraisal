package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void toSelection(View v) {
    // TODO Bring the user from main menu, to experiment type menu
    Intent intent = new Intent(this, ExpSubscriptionActivity.class);
    startActivity(intent);
  }

  public void toCheckUsername(View v) {
    // TODO Bring the user from main menu, to experiment type menu
    Intent intent = new Intent(this, AuthenticationActivity.class);
    startActivity(intent);
  }
}
