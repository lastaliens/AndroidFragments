package org.o7planning.androidcamerademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView imageView;

    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    private static final int REQUEST_ID_VIDEO_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonImage = this.findViewById(R.id.ImageBtn);
        Button buttonVideo = this.findViewById(R.id.VideoBtn);

        buttonImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        buttonVideo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndCaptureVideo();
            }
        });
    }

    private void captureImage() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File dir = Environment.getExternalStorageDirectory();

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String savePath = dir.getAbsolutePath() + "/myimage.jpg";
            File imageFile = new File(savePath);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        } catch(Exception e) {
            Toast.makeText(this, "Error capture image: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void askPermissionAndCaptureVideo() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_ID_READ_WRITE_PERMISSION
                );
                return;
            }
        }
        this.captureVideo();
    }

    private void captureVideo() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File dir = Environment.getExternalStorageDirectory();

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String savePath = dir.getAbsolutePath() + "/myvideo.mp4";
            File videoFile = new File(savePath);
            Uri videoUri = Uri.fromFile(videoFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            this.startActivityForResult(intent, REQUEST_ID_VIDEO_CAPTURE);
        } catch(Exception e) {
            Toast.makeText(this, "Error capture video: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_ID_READ_WRITE_PERMISSION) {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                this.imageView.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_ID_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Uri videoUri = data.getData();
                Log.i("MyLog", "Video saved to: " + videoUri);
                Toast.makeText(this, "Video saved to:\n" + videoUri, Toast.LENGTH_LONG).show();

                this.videoView.setVideoURI(videoUri);
                this.videoView.start();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action Cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}