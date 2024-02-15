package org.vinegarhq.redeye;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Open camera for creating document from camera
        FloatingActionButton newDocumentCamera = findViewById(R.id.newDocumentCamera);
        newDocumentCamera.setOnClickListener(v -> {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "Missing Camera Permission!", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[] {android.Manifest.permission.CAMERA}, 0);
            } else {
                // Jump to camera activity here
                Intent intent = new Intent(MainActivity.this, CameraCaptureActivity.class);
                startActivity(intent);

            }
        });

        // Open image library for creating document from library
        FloatingActionButton newDocumentImage = findViewById(R.id.newDocumentImage);
        // Open gallery here.
        newDocumentImage.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "not implemented!", Toast.LENGTH_SHORT).show());
    }
}