package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import com.suju02.android_mini_project.file_adapters.Single_list;

import java.io.File;
import java.util.ArrayList;

public class list_images extends AppCompatActivity {

    Single_list single_list;
    String parent;
    File imgs_parent;
    LinearLayoutManager manager;
    ArrayList<File> imgs_arr;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);
        getSupportActionBar().hide();

        parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        imgs_parent = new File(parent);
        rv = findViewById(R.id.image_list_home_to_list_act);
        imgs_arr = new ArrayList<File>();
        single_list = new Single_list(this,imgs_arr);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(single_list);
        show_files(imgs_parent);
    }

    public void show_files(File file)
    {
        File[] File = file.listFiles();
        if(File != null && File.length != 0)
        { for(File Files : File) { if(Files.isFile() || Files.isDirectory()) { imgs_arr.add(Files); } } }
    }
}