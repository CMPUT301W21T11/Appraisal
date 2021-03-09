package com.example.appraisal.UI;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.model.AuthenticationModel;

public class AuthenticationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    AuthenticationModel auth = new AuthenticationModel();
    auth.sign_in();

    TextView user_id = (TextView)findViewById(R.id.user_id);
    user_id.setText(auth.get_userID());
  }
}
