package com.suju02.android_mini_project;

/*****/  import com.suju02.android_mini_project.file_adapters.Single_list; /*****/
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class list_video extends AppCompatActivity {

    File parent_path;
    Single_list single_list;
    ArrayList<File> tmp1_returned;
    RecyclerView rv;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        getSupportActionBar().hide();

        parent_path = new File(Environment.getExternalStorageDirectory().getPath());
        rv = findViewById(R.id.video_list_home_to_list_act);
        tmp1_returned=new ArrayList<File>();
        single_list = new Single_list(this,tmp1_returned);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(single_list);
        show_files(parent_path);
    }

    public void show_files(File file)
    {
        File[] File = file.listFiles();
        if(File != null)
        { for(File Files : File) { if((Files.isFile()) && (Files.getName().toLowerCase().endsWith(".mp4") || Files.getName().toLowerCase().endsWith(".mkv"))) { tmp1_returned.add(Files); } if(Files.isDirectory()) { get_sub_dir_files(Files); } } }
    }

    public void get_sub_dir_files(File file)
    {
        File[] File = file.listFiles();
        if(File != null)
        { for(File Files : File) { if(Files.isDirectory()) { show_files(Files); } else if(Files.isFile() && (Files.getName().toLowerCase().endsWith(".mkv") || Files.getName().toLowerCase().endsWith(".mp4"))) { tmp1_returned.add(Files); } } }
    }
}