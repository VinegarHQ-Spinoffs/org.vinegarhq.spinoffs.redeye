package org.vinegarhq.redeye;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create cache directory for image storage
        String uniqueId = UUID.randomUUID().toString();
        File cacheDirectory = new File(this.getCacheDir(), uniqueId);
        cacheDirectory.mkdirs();
        Log.d("CameraCaptureActivity", cacheDirectory.getAbsolutePath()); // TODO: Remove this debug code

        // Open camera for creating document from camera
        FloatingActionButton newDocumentCamera = findViewById(R.id.newDocumentCamera);
        newDocumentCamera.setOnClickListener(v -> {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "Missing Camera Permission!", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[] {android.Manifest.permission.CAMERA}, 0);

                // TODO: Automatically start camera when permission granted
            } else {
                // Jump to camera activity here
                Intent intent = new Intent(MainActivity.this, CameraCaptureActivity.class);

                intent.putExtra("cachePath", cacheDirectory.toString());
                startActivity(intent);

            }
        });

        // Open image library for creating document from library
        FloatingActionButton newDocumentImage = findViewById(R.id.newDocumentImage);
        // Open gallery here.
        newDocumentImage.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "not implemented!", Toast.LENGTH_SHORT).show());
    }
}