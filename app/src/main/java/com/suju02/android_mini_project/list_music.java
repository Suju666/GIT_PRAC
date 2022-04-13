package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Environment;
import com.suju02.android_mini_project.file_adapters.Single_list;
import java.io.File;
import java.util.ArrayList;

public class list_music extends AppCompatActivity {

    Single_list single_list;
    File music_parent;
    RecyclerView rv;
    LinearLayoutManager manager;
    ArrayList<File> music_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        getSupportActionBar().hide();

        music_parent = new File(Environment.getExternalStorageDirectory().getPath());
        rv = findViewById(R.id.music_list_home_to_list_act);
        music_arr = new ArrayList<File>();
        single_list = new Single_list(this,music_arr);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(single_list);
        show_files(music_parent);
    }

    public void show_files(File tmp)
    {
        File[] files = tmp.listFiles();
        if(files != null)
        {
            for(File file : files) { if((file.isFile()) && (file.getName().toLowerCase().endsWith(".mp3") || file.getName().toLowerCase().endsWith(".m4a"))) { music_arr.add(file); } if(file.isDirectory()) { getsub(file); } }
        }
    }

    public void getsub(File tmp)
    {
        File[] files = tmp.listFiles();
        if(files != null)
        {
            for(File file : files) { if(file.isDirectory()) { show_files(file); } else if((file.isFile()) && (file.getName().toLowerCase().endsWith(".mp3") || file.getName().toLowerCase().endsWith(".m4a"))) { music_arr.add(file); } }
        }
    }
}