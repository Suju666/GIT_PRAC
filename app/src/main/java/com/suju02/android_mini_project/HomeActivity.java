package com.suju02.android_mini_project;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        check();
//       ex_path = Environment.getStorageDirectory().getPath()+"/0000-AD72";
//        File[] get_list = this.getExternalCacheDirs();
//        for(File f : get_list)
//        {
//            if(Environment.isExternalStorageRemovable(f)) {
//                ex_path = f.getPath();//.split("/Android")[0];
//                break;
//            }
//        }
//        ex_path = Environment.getStorageDirectory().getPath();
    }

    public void check()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if((getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && getApplicationContext().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            { }
            else
            { requestPermissions( new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},2);}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 2)
        {
            if((grantResults[0] == PackageManager.PERMISSION_DENIED) && grantResults[1] == PackageManager.PERMISSION_DENIED)
            { Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show(); finish();; }
            else
            {
                Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void redirect_to_download(View v)
    {
        Intent send_to_download_act = new Intent(this,downlaod.class);
        startActivity(send_to_download_act);
    }

    public void redirect_to_images(View v)
    {
        Intent send_to_images_act = new Intent(this,list_images.class);
        startActivity(send_to_images_act);
    }

    public void redirect_to_music(View v)
    {
        Intent send_to_music_act = new Intent(this,list_music.class);
        startActivity(send_to_music_act);
    }

    public void redirect_to_videos(View v)
    {
        Intent send_to_video_act = new Intent(this,list_video.class);
        startActivity(send_to_video_act);
    }

    public void redirect_to_document(View v)
    {
        Intent send_to_document_act = new Intent(this,list_documents.class);
        startActivity(send_to_document_act);
    }

    public void redirect_to_apk(View v)
    {
        Intent send_to_apks_act = new Intent(this,list_apks.class);
        startActivity(send_to_apks_act);
    }

    public void direct_to_foldersandfiles(View v)
    {
        Intent intnt1 = new Intent(this,FileList.class);
        startActivity(intnt1);
    }

    public void direct_to_foldersandfiles_external(View v)
    {
        Intent intnt2 = new Intent(this,FileList2.class);
        startActivity(intnt2);
    }
}