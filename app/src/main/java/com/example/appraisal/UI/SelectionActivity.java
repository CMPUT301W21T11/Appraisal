package com.example.appraisal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.backend.BinomialTrial;

public class SelectionActivity extends AppCompatActivity {
  // TODO
  // Needs 4 button action for the user to choose the experiment type, then
  // bring the user to the corresponding activity

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection);
  }

  public void toBinomial(View v) {
    Intent binomial_intent = new Intent(this, BinomialActivity.class);
    startActivity(binomial_intent);
  }

  public void toCounter(View v) {
    Intent intent = new Intent(this, CounterActivity.class);
    startActivity(intent);
  }

  public void toMeasurement(View v) {
    Intent intent = new Intent(this, MeasurementActivity.class);
    startActivity(intent);
  }

  public void toDetails(View v) {
    Intent intent = new Intent(this, SpecificExpActivity.class);
    startActivity(intent);
  }
  public void toIntCount(View v) {
    Intent intent = new Intent(this, NonNegIntCountActivity.class);
    startActivity(intent);
  }
}
