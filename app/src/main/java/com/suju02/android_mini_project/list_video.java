package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;

/***** *****/ import com.suju02.android_mini_project.home_screen_shortchuts.get_all_download_list; /***** *****/

public class list_video extends AppCompatActivity {

    String videos_parent_path;
    get_all_download_list obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);

        videos_parent_path = Environment.getExternalStorageDirectory().getPath();
        obj = new get_all_download_list(videos_parent_path);
    }

}