package org.vinegarhq.redeye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class CameraCaptureActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);

        // Per Android Documentation
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // You shouldn't be able to get here. This is just to make java shut up.
            }
        }, ContextCompat.getMainExecutor(this));

    }


    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

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
        takePhoto.setOnClickListener(v -> imageCapture.takePicture(getMainExecutor(), new ImageCapture.OnImageCapturedCallback()
        {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {

                Bitmap bitmap = imageProxy.toBitmap();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

                Intent intent = new Intent(CameraCaptureActivity.this, EditImageActivity.class);
                intent.putExtra("bitmap", bs.toByteArray());
                startActivity(intent);

            }
        }));
    }
}