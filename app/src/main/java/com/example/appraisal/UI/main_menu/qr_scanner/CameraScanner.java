package com.example.appraisal.UI.main_menu.qr_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.appraisal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CameraScanner extends AppCompatActivity {
    // This activity is heavily based on CameraX's documentation
    // Author: Google
    // URL: https://developer.android.com/training/camerax

    private ImageCapture imageCapture;
    private File outputDir;
    private ExecutorService cameraExecutor;

    private final String TAG = "CameraX_QR_Barcode_Scanner";
    private final String FILENAME_FORMAT = "MM-dd-yyyy-HH-mm-ss-SSS";
    private final int REQUEST_CODE_PERMISSION = 10;
    private final List<String> REQUIRED_PERMISSIONS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_barcode_qr_scanner);

        REQUIRED_PERMISSIONS.add(Manifest.permission.CAMERA);

        // Check and ask for permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS.toArray(new String[0]), REQUEST_CODE_PERMISSION);
        }

        FloatingActionButton take_photo_button = findViewById(R.id.camera_scanner_camera_capture_button);

        take_photo_button.setOnClickListener(v -> takePhoto());

    }

    private void startCamera() {

    }

    private void takePhoto() {

    }

    @NonNull
    private File getOutputDir() {
        File[] dirs = getExternalMediaDirs(); // get external media dir
        if (dirs != null) {
            File mediaDir = dirs[0];
            File new_media_dir = new File(mediaDir, getResources().getString(R.string.app_name));

            if ((new_media_dir != null) && new_media_dir.exists()) {
                return new_media_dir;
            } else {
                // return default file dir otherwise
                return getFilesDir();
            }
        } else {
            // return default file dir otherwise
            return getFilesDir();
        }
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
    /**
     * Shut down the camera executor when activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}