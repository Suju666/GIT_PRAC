
package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;

public class FileList extends AppCompatActivity {

    File fpath;
    String mpath;
    ArrayList<File> fileArray;
    RecyclerView rv;

    public static FilesAdapter filesAdapter;
    LinearLayoutManager manager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        getSupportActionBar().hide();

        rv = findViewById(R.id.internal_files_list);
        fileArray=new ArrayList<File>();
        filesAdapter=new FilesAdapter(this,fileArray);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(filesAdapter);

        mpath = getIntent().getStringExtra("path");
        fpath = new File(mpath);
        show_files(fpath);
    }

    public void show_files(File path)
    {
        File[] file = path.listFiles();
        if(file != null && file.length != 0)
        {
            for(File files : file)
            {
                if(files.isDirectory() || files.isFile()) { fileArray.add(files); }
            }
        }
    }
}