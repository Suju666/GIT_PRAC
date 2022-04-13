package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Environment;

import com.suju02.android_mini_project.file_adapters.Single_list;

import java.io.File;
import java.util.ArrayList;

public class list_apks extends AppCompatActivity {

    ArrayList<File> fileArrayList;
    RecyclerView recyclerView;
    Single_list single_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apks);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.apk_home_to_list_act);
        fileArrayList = new ArrayList<>();
        single_list = new Single_list(this,fileArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(single_list);
        File dir = new File(Environment.getExternalStorageDirectory().getPath());
        show_files(dir);
    }

    public void show_files(File tmp)
    {
        File[] files = tmp.listFiles();
        if(files != null)
        {
            for(File file : files) { if((file.isFile()) && (file.getName().toLowerCase().endsWith(".apk"))) { fileArrayList.add(file); } if(file.isDirectory()) { getsub(file); } }
        }
    }

    public void getsub(File tmp)
    {
        File[] files = tmp.listFiles();
        if(files != null)
        {
            for(File file : files) { if(file.isDirectory()) { show_files(file); } else if((file.isFile()) && (file.getName().toLowerCase().endsWith(".apk"))) { fileArrayList.add(file); } }
        }
    }
}