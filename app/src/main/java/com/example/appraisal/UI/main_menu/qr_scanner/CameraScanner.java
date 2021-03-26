package com.example.appraisal.UI.main_menu.qr_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import com.example.appraisal.R;
import com.example.appraisal.model.QRAnalyzerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraScanner extends AppCompatActivity {
    // This activity is heavily based on CameraX's documentation
    // Author: Google
    // URL: https://developer.android.com/training/camerax

    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;

    private PreviewView preview_view;
    private FloatingActionButton take_photo_button;

    private ListenableFuture<ProcessCameraProvider> cameraProvider;
    private QRAnalyzerModel model;
    private File outputDirectory;

    private final String TAG = "CameraX_QR_Barcode_Scanner";
    private final String FILENAME_FORMAT = "MM-dd-yyyy-HH-mm-ss-SSS";
    private final int REQUEST_CODE_PERMISSION = 10;
    private final List<String> REQUIRED_PERMISSIONS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_barcode_qr_scanner);

        REQUIRED_PERMISSIONS.add(Manifest.permission.CAMERA);
        preview_view = findViewById(R.id.camera_scanner_viewFinder);
        take_photo_button = findViewById(R.id.camera_scanner_camera_capture_button);
        model = new QRAnalyzerModel();
        outputDirectory = getOutputDirectory();
        cameraExecutor = Executors.newSingleThreadExecutor();

        // Check and ask for permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS.toArray(new String[0]), REQUEST_CODE_PERMISSION);
        }

        take_photo_button.setOnClickListener(v -> takePhoto());
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
                Toast toast = new Toast(this);
                toast.setText("Error: Permission denied");
                toast.show();
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults); // call super for other request code
        }
    }

    private void startCamera() {
        // create an instance of the process camera provider
        cameraProvider = ProcessCameraProvider.getInstance(this);
        Activity self = this; // store this activity
        cameraProvider.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider provider = cameraProvider.get();
                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(preview_view.getSurfaceProvider());

                    bindImageAnalysis(provider);
                    CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                    imageCapture = new ImageCapture.Builder().build();

                    provider.unbindAll();
                    provider.bindToLifecycle((LifecycleOwner) self, cameraSelector, preview);

                } catch (Exception e) {
                    Log.e("Error:", "start camera failed:");
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));

    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider provider) {
        // Build image analysis object that is QR Analyzer compatible
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(preview_view.getWidth(), preview_view.getHeight()))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), model);
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }
        Activity self = this; // save base activity
        DateFormat format = new SimpleDateFormat(FILENAME_FORMAT, Locale.CANADA);
        String child_file_name = format.format(Calendar.getInstance().getTime()) + ".jpg";
        File photoFile = new File(outputDirectory, child_file_name);

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = Uri.fromFile(photoFile);
                Toast message = new Toast(self);
                message.setText("Photo capture succeeded: " + savedUri);
                message.setDuration(Toast.LENGTH_SHORT);
                message.show();
                Log.d("Debug:","Camera capture successful");
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("Error:", "Capture failed");
                exception.printStackTrace();
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

    private File getOutputDirectory() {
        // This method get the possible output directory for the photos
        File[] externalMediaDirs = getExternalMediaDirs();
        if (externalMediaDirs == null) {
            return getFilesDir();
        }

        File mediaDir = externalMediaDirs[0];
        mediaDir = new File(mediaDir, getResources().getString(R.string.app_name));
        boolean isSuccess = mediaDir.mkdirs();
        if (mediaDir != null && mediaDir.exists()) {
            Log.d("Debug:", "Media creation success:" + isSuccess);
            return mediaDir;
        } else {
            return getFilesDir();
        }
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