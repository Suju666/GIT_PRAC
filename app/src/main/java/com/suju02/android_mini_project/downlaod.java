package com.suju02.android_mini_project;

/*****/  import com.suju02.android_mini_project.file_adapters.Single_list; /*****/
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class downlaod extends AppCompatActivity {

    String parent;
    File parent_path;
    Single_list single_list;
    ArrayList<File> tmp1_returned;
    RecyclerView rv;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downlaod);
        getSupportActionBar().hide();

        parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        parent_path = new File(parent);
        rv = findViewById(R.id.download_home_to_list_act);
        tmp1_returned=new ArrayList<File>();
        single_list = new Single_list(this,tmp1_returned);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(single_list);
        show_files(parent_path);
    }

    public void show_files(File path)
    {
        File[] file = path.listFiles();
        if(file != null && file.length != 0)
        { for(File files : file) { if (files.isDirectory() || files.isFile()) { tmp1_returned.add(files); } } }
    }
}