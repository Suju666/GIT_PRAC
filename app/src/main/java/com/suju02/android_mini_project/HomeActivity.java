package com.suju02.android_mini_project;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static RecyclerView rv;
    String path;
    public static ArrayList<String> arrayList_nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide(); // hiding action bar
        check(); // calling permission method
       path = Environment.getExternalStorageDirectory().getPath(); // path
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
        Intent send_to_video_act = new Intent(this,list_video.class);
        startActivity(send_to_video_act);
    }

    public void redirect_to_document(View v)
    {
        Intent send_to_document_act = new Intent(this,list_documents.class);
        startActivity(send_to_document_act);
    }

    public void direct_to_foldersandfiles(View v)
    {
        Intent intnt = new Intent(this,FileList.class);
        intnt.putExtra("path",path);
        startActivity(intnt);
    }


    /*
            arrayList_nam.clear();
        File files[] = path.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) { arrayList_nam.add(file); }
            }
            for (File file : files) {
                if (!file.isDirectory()) { arrayList_nam.add(0, file); }
            }
        }
     */
}