package com.example.appraisal.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.NonNegIntCountModel;

public class NonNegCountActivity extends AppCompatActivity {

  private NonNegIntCountModel model;
  private EditText counter_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nonneg_count_layout);

    counter_view = findViewById(R.id.nonneg_count_input);
    model = new NonNegIntCountModel();
  }

  public void saveAndReturn(View v) {
    // Adjust the model
    String user_input = counter_view.getText().toString();
    try {
      model.saveCount(user_input);
    } catch (NumberFormatException e) {
      Log.d("Warning", "User input caused integer overflow");
      return;
    }
    finish();
  }
}