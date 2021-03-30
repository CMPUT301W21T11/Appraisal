package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.core.MainModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGeneratorActivity extends AppCompatActivity {

    private ImageView qr_code;
    private EditText trial_value_qr_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_qr);

        qr_code = findViewById(R.id.qr_code);
        trial_value_qr_input = findViewById(R.id.trial_value_qr_input);

        MultiFormatWriter writer = new MultiFormatWriter();

        Experiment current_exp = null;
        try {
            current_exp = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String value = getIntent().getStringExtra("val");
        String message = "Appraisal;" + value + ";" + current_exp.getType().toString() + ";" + current_exp.getExpId();

        BitMatrix bit_matrix = null;
        try {
            bit_matrix = writer.encode(message, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = encoder.createBitmap(bit_matrix);

        qr_code.setImageBitmap(bitmap);
    }

}
