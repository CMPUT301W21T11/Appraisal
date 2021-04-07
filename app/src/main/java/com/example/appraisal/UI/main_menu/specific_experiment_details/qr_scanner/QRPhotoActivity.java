package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * This class generates the QR code to be displayed
 */
public class QRPhotoActivity extends AppCompatActivity {

    private ImageView qr_code;
    private EditText trial_value_qr_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_qr);

        qr_code = findViewById(R.id.qr_code);
        TextView exp_title = findViewById(R.id.activity_generator_qr_exp_title);
        TextView trial_type = findViewById(R.id.activity_generator_qr_trial_value);
        Button finish_button = findViewById(R.id.generator_qr_finish);
        finish_button.setOnClickListener(v -> finish());

        MultiFormatWriter writer = new MultiFormatWriter();

        Experiment current_exp;
        try {
            current_exp = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        exp_title.setText(current_exp.getDescription());
        String value = getIntent().getStringExtra("val");
        String message = getResources().getString(R.string.app_name) + ";" + current_exp.getType() + ";" + value + ";" + current_exp.getExpId();

        TrialType exp_type = TrialType.getInstance(current_exp.getType());
        switch (exp_type) {
            case BINOMIAL_TRIAL:
                if (value.equalsIgnoreCase("1")) {
                    trial_type.setText("Binomial Trial: SUCCESS");
                } else {
                    trial_type.setText("Binomial Trial: FAILURE");
                }
                break;
            case MEASUREMENT_TRIAL:
                trial_type.setText("Measurement Trial: " + value);
                break;
            case NON_NEG_INT_TRIAL:
                trial_type.setText("Non Negative Integer Trial: " + value);
                break;
            default:
                trial_type.setText("Count Trial + 1");
        }

        BitMatrix bit_matrix;
        try {
            bit_matrix = writer.encode(message, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
            return;
        }
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = encoder.createBitmap(bit_matrix);

        qr_code.setImageBitmap(bitmap);
    }

}
