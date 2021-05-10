package com.nitolmotors.retrofitimageupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageUploadASPServiceActivity extends AppCompatActivity {

    private OurRetrofitClient ourRetrofitClient;
    private EditText et;
    private Button btnBrowseImage, btnBrowse, btnUpload;
    private ImageView img;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_aspservice);

        et = findViewById(R.id.et);
        btnBrowseImage = findViewById(R.id.btnBrowseImage);
        btnBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                Intent result = Intent.createChooser(intent, "Choose File");
                startActivityForResult(result, 100);
            }
        });

        btnBrowse = findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/*");
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                Intent result = Intent.createChooser(intent, "Choose File");
                startActivityForResult(result, 200);
            }
        });

        btnUpload = findViewById(R.id.btnUpoad);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        img = findViewById(R.id.img);

        verifyStoragePermissions(ImageUploadASPServiceActivity.this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.13/fileupload/api/").addConverterFactory(GsonConverterFactory.create()).build();
        ourRetrofitClient = retrofit.create(OurRetrofitClient.class);

    }

    private void uploadImage() {
        try {

            File file = new File(imagePath);
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), et.getText().toString());

            Call<ResponseClass> call = ourRetrofitClient.Upload(photo, description);
            call.enqueue(new Callback<ResponseClass>() {
                @Override
                public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                    Toast.makeText(ImageUploadASPServiceActivity.this, "Photo upload seccessfull.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseClass> call, Throwable t) {
                    Toast.makeText(ImageUploadASPServiceActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception ex) {
            Toast.makeText(this, "Failed Exception found.", Toast.LENGTH_SHORT).show();

        }
    }

    String imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                imagePath = getPathFromUri(uri);
                img.setImageURI(uri);
            }
        } else if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                imagePath = getPathFromUri(uri);
                img.setImageURI(uri);
            }
        }

    }

    public String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
