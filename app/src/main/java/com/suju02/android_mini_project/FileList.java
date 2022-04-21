package com.suju02.android_mini_project;

/*****/ import com.suju02.android_mini_project.file_adapters.FilesAdapter; /*****/ // importing filesadapter from file_adapters package.
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

public class FileList extends AppCompatActivity {

    public static File fpath;
    private static View paste, layout_in, layout_in2, newFold;
    String mpath;
    public static ArrayList<File> fileArray;
    RecyclerView rv;
    public static Boolean user=false;
    public static FilesAdapter filesAdapter;
    LinearLayoutManager manager;
    static String orig=null;
    public static File copy_file, paste_file, file_mv, file_ps;
    public static ArrayList<String> final_selected_dirs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        getSupportActionBar().hide();

        final_selected_dirs = new ArrayList<>();

        layout_in = findViewById(R.id.for_paste);
        layout_in2 = findViewById(R.id.for_new);
        paste = findViewById(R.id.img_paste);
        newFold = findViewById(R.id.img_newFolder);
        rv = findViewById(R.id.internal_files_list);
        fileArray=new ArrayList<File>();
        filesAdapter=new FilesAdapter(this,fileArray);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(filesAdapter);

        mpath = Environment.getExternalStorageDirectory().getPath();
        Log.d("Checking",mpath.toString());
        fpath = new File(mpath);
            show_files(fpath);

        newFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View xv = LayoutInflater.from(FileList.this).inflate(R.layout.name_assign_folder,null);
                TextView tv;
                EditText et;
                tv = xv.findViewById(R.id.set_nam);
                et = xv.findViewById(R.id.folder_nu_nam);

                AlertDialog dialog = new AlertDialog.Builder(FileList.this).setView(xv).setCancelable(false).create();
                dialog.show();

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String file_nam = et.getText().toString();
                        final File newFile = new File(fpath+"/"+file_nam);
                        newFile.mkdir();
                        Log.d("Checking",newFile.getPath());
                        filesAdapter.notifyDataSetChanged();
                        show_files(new File(String.valueOf(fpath)));
                    }
                });
            }
        });
    }

    public static void show_files(File path)
    {fileArray.clear();
        File[] file = path.listFiles();
        if(file != null)
        {
            for (File files : file) {
                if (files.isDirectory()) {
                    if ((files.isDirectory()) && (!files.isHidden())) {
                        fileArray.add(files);
                    }
                }

                if (files.isFile()) {
                    if ((files.isFile()) && (!files.isHidden())) {
                        fileArray.add(files);
                    }
                }
            }
        }
        Collections.sort(fileArray);
        filesAdapter.notifyDataSetChanged();
    }

    public static void copyF()
    {
        layout_in2.setVisibility(View.INVISIBLE);
        user = true;
        FilesAdapter.track = user;
        orig = FilesAdapter.get_current_selected_itm();
        Toast.makeText(paste.getContext(), "Copied "+orig,Toast.LENGTH_LONG).show();
        file_mv = new File(orig);
        Toast.makeText(paste.getContext(), "Copied "+file_mv.getPath(),Toast.LENGTH_SHORT).show();
        FilesAdapter.files_mdls.clear();
        filesAdapter.notifyDataSetChanged();
        show_files(new File(String.valueOf(fpath)));
        layout_in.setVisibility(View.VISIBLE);
    }
    @RequiresApi(api = VERSION_CODES.N & VERSION_CODES.Q)
    public void paste_musibat(View view)
    {
        StringJoiner joiner = new StringJoiner("/");
        final_selected_dirs = FilesAdapter.track_my_user_move;
        for(String ek_ek : final_selected_dirs)
        {
            joiner.add(ek_ek);
        }
        String paste_path = joiner.toString() + "/" + file_mv.getName();
        file_ps = new File(paste_path);
        File file_ps_dup = new File(file_ps.getPath());
        File file_mv_dup = new File(file_mv.getPath());
        Toast.makeText(layout_in.getContext(), file_ps.getPath().toString(),Toast.LENGTH_LONG).show();
        try
        {
            FileUtils.copy(new FileInputStream(file_mv_dup),new FileOutputStream(file_ps_dup));
        }
        catch (Exception e) {}
        FilesAdapter.track_my_user_move.clear();
        FilesAdapter.track_my_user_move.add(new File(String.valueOf(fpath)).getPath());
        final_selected_dirs.clear();
        user = false;
        FilesAdapter.track=user;
        Toast.makeText(paste.getContext(), "Moving will"+user, Toast.LENGTH_SHORT).show();
        layout_in.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        int get_size = FilesAdapter.last_pos.size();
//        int get_size_x = FilesAdapter.track_my_user_move.size();
        if(get_size>0) {
            get_size -= 1;
//            get_size_x -= 1;
            FilesAdapter.last_pos.remove(get_size);
            if(get_size >= 1)
            {
                File temp_file_path = FilesAdapter.last_pos.get(get_size-1);
                filesAdapter.notifyDataSetChanged();
                String path = temp_file_path.getPath();
                File temp = new File(path);
                show_files(temp);
            }
            if(get_size == 0)
            { show_files(fpath);}
        }
        else
        { super.onBackPressed(); }
    }
}