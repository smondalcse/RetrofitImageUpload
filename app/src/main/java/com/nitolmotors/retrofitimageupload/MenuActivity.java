package com.nitolmotors.retrofitimageupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button uploadwithphp = findViewById(R.id.uploadwithphp);
        uploadwithphp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button uploadwithasp = findViewById(R.id.uploadwithasp);
        uploadwithasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ImageUploadASPServiceActivity.class);
                startActivity(intent);
            }
        });

        Button download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DownloadFileActivity.class);
                startActivity(intent);
            }
        });

        Button downloadWithNotificationBar = findViewById(R.id.downloadWithNotificationBar);
        downloadWithNotificationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DownloadWithNotificationBarActivity.class);
                startActivity(intent);
            }
        });


    }
}
