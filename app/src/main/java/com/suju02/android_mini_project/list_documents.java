package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Environment;
import com.suju02.android_mini_project.file_adapters.Single_list;
import java.io.File;
import java.util.ArrayList;

public class list_documents extends AppCompatActivity {

    Single_list single_list;
    String parent;
    File documnet_parent;
    ArrayList<File> doc_arr;
    RecyclerView rv;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_documents);
        getSupportActionBar().hide();

        parent = Environment.getExternalStorageDirectory().getPath();
        documnet_parent = new File(parent);
        rv = findViewById(R.id.document_list_home_to_list_act);
        doc_arr = new ArrayList<>();
        single_list = new Single_list(this,doc_arr);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(single_list);
        show_files(documnet_parent);
    }

    public void show_files(File file)
    {
        File[] File = file.listFiles();
        if(File != null)
        {
            for(File Files : File) { if((Files.isFile()) && (Files.getName().toLowerCase().endsWith(".pdf") || Files.getName().toLowerCase().endsWith(".doc") || Files.getName().toLowerCase().endsWith(".docx") || Files.getName().toLowerCase().endsWith(".ppt") || Files.getName().toLowerCase().endsWith(".pptx") || Files.getName().toLowerCase().endsWith(".exls") || Files.getName().toLowerCase().endsWith(".csv"))) { doc_arr.add(Files); }
            if (Files.isDirectory()) { get_sub_dir_f(Files); } }
        }
    }

    public void get_sub_dir_f(File file)
    {
        File[] Files = file.listFiles();
        if(Files != null)
        {
            for(File File : Files) { if(File.isDirectory()) { show_files(File); } else if((File.isFile()) && (File.getName().toLowerCase().endsWith(".pdf") || File.getName().toLowerCase().endsWith(".doc") || File.getName().toLowerCase().endsWith(".docx") || File.getName().toLowerCase().endsWith(".ppt") || File.getName().toLowerCase().endsWith(".pptx") || File.getName().toLowerCase().endsWith(".exls") || File.getName().toLowerCase().endsWith(".csv"))) { doc_arr.add(File); } }
        }
    }
}