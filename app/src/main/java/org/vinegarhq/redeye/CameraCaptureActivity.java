package org.vinegarhq.redeye;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CameraCaptureActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);

        // Create a temporary file for writing.
        File cachePath;
        File imageFile;
        if (getIntent().getExtras() != null) {
            cachePath = new File(Objects.requireNonNull(getIntent().getExtras().getString("cachePath")));
            try {
              imageFile = File.createTempFile(UUID.randomUUID().toString(), ".png", cachePath);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        } else {
            throw new RuntimeException(); // data loss moment
        }

        // Per Android Documentation
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, imageFile, cachePath);
            } catch (ExecutionException | InterruptedException e) {
                // You shouldn't be able to get here. This is just to make java shut up.
            }
        }, ContextCompat.getMainExecutor(this));

    }


    void bindPreview(@NonNull ProcessCameraProvider cameraProvider, File imageFile, File cachePath) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        PreviewView previewView = (PreviewView) findViewById(R.id.previewView);

        preview.setSurfaceProvider(previewView.getSurfaceProvider());



        // Set up image capture
        ImageCapture imageCapture = new ImageCapture.Builder()
                .setTargetRotation(findViewById(android.R.id.content).getRootView().getDisplay().getRotation())
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture, preview);

        // TODO: Perhaps add flash photography at some point in the future? Not clear if there is impact on document quality.

        // Handle takePhoto button pressed

        FloatingActionButton takePhoto = findViewById(R.id.confirmEdit);

        ImageCapture.OutputFileOptions fileOptions = new ImageCapture.OutputFileOptions.Builder(imageFile)
                .build();
        takePhoto.setOnClickListener(v -> imageCapture.takePicture(fileOptions, getMainExecutor(), new ImageCapture.OnImageSavedCallback()
        {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Intent intent = new Intent(CameraCaptureActivity.this, EditImageActivity.class);
                intent.putExtra("imageFile", imageFile.toString());
                //intent.putExtra("cachePath", cachePath.toString());
                // maybe redundant to convert to string again?
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                throw new RuntimeException();
            }
        }));
    }
}