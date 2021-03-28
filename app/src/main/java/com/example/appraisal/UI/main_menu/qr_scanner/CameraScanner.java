package com.example.appraisal.UI.main_menu.qr_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.appraisal.R;
import com.example.appraisal.model.CameraScannerModel;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

public class CameraScanner extends AppCompatActivity {
    // This activity is heavily based the following video tutorial
    // Author: SmallAcademy (https://www.youtube.com/channel/UCR1t5eSmLxLUdBnK2XwZOuw)
    // URL: https://www.youtube.com/watch?v=Iuj4CuWjYF8

    private CodeScannerView scanner_view;
    private CameraScannerModel model;

    private final int REQUEST_CODE_PERMISSION = 10;
    private final List<String> REQUIRED_PERMISSIONS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_barcode_qr_scanner);

        REQUIRED_PERMISSIONS.add(Manifest.permission.CAMERA);
        scanner_view = findViewById(R.id.camera_scanner_viewFinder);
        model = new CameraScannerModel(this, scanner_view);
        // Check and ask for permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS.toArray(new String[0]), REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * This method get the permission results to check if the necessary permissions are granted
     * @param requestCode -- the code for this permission request
     * @param permissions -- All the requested permission
     * @param grantResults -- the result if the permission is granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) { // check for only the specific request code permission
            if(allPermissionsGranted()) {
                startCamera();
            } else { // inform user that permission is denied
                Toast toast = Toast.makeText(this, "Error: Permission denied", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults); // call super for other request code
        }
    }

    /**
     * This prevents lockup of camera scanner
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (allPermissionsGranted()) {
            model.startScanner();
        }
    }

    /**
     * This make sure camera scanner is properly paused
     */
    @Override
    protected void onPause() {
        model.pauseScanner();
        super.onPause();
    }

    private void startCamera() {
        model.enableCodeScannerViewRefresh();
        model.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(() -> {
                    model.storeBarCode(result);
                    finish();
                });
            }
        });
    }

    private boolean allPermissionsGranted() {
        // check if all permissions are granted
        for (String permission: REQUIRED_PERMISSIONS) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}