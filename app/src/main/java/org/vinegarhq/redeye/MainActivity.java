package org.vinegarhq.redeye;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Open camera for creating document from camera
        FloatingActionButton newDocumentCamera = (FloatingActionButton) findViewById(R.id.newDocumentCamera);
        newDocumentCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "Missing Camera Permission!", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[] {android.Manifest.permission.CAMERA}, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "not implemented!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Open image library for creating document from library
        FloatingActionButton newDocumentImage = (FloatingActionButton) findViewById(R.id.newDocumentImage);
        newDocumentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "not implemented!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}