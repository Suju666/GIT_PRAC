package com.suju02.android_mini_project;

/*****/
import com.suju02.android_mini_project.file_adapters.FilesAdapter2; /*****/
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
import java.util.Comparator;
import java.util.StringJoiner;

public class FileList2 extends AppCompatActivity {

    public File fnl_show;
    public static File fpath;
    private static View paste, layout_in, layout_in2, newFold;
    static String mpath;
    public static ArrayList<File> fileArray;
    RecyclerView rv;
    public static Boolean user=false;
    public static FilesAdapter2 filesAdapter2;
    LinearLayoutManager manager;
    static String orig=null;
    public static File copy_file, paste_file, file_mv, file_ps;
    public static ArrayList<String> final_selected_dirs;

    @RequiresApi(api = VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list2);
        getSupportActionBar().hide();

        final_selected_dirs = new ArrayList<>();

        layout_in = findViewById(R.id.for_paste);
        layout_in2 = findViewById(R.id.for_new);
        paste = findViewById(R.id.img_paste);
        newFold = findViewById(R.id.img_newFolder);
        rv = findViewById(R.id.internal_files_list);
        fileArray=new ArrayList<File>();
        filesAdapter2=new FilesAdapter2(this,fileArray);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(filesAdapter2);

        mpath = Environment.getStorageDirectory().getPath();
        Log.d("Checking",mpath.toString());
        fpath = new File(mpath);
        fnl_show = select_one(fpath);
        show_files(fnl_show);

        newFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View xv = LayoutInflater.from(FileList2.this).inflate(R.layout.name_assign_folder,null);
                TextView tv;
                EditText et;
                tv = xv.findViewById(R.id.set_nam);
                et = xv.findViewById(R.id.folder_nu_nam);

                AlertDialog dialog = new AlertDialog.Builder(FileList2.this).setView(xv).setCancelable(false).create();
                dialog.show();

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String file_nam = et.getText().toString();
                        final File newFile = new File(fnl_show+"/"+file_nam);
                        newFile.mkdir();
                        Log.d("Checking",newFile.getPath());
                        filesAdapter2.notifyDataSetChanged();
                        show_files(fnl_show);
                    }
                });
            }
        });
    }

    public File select_one(File f_one)
    {
        File fnl = null;
        File[] f_temp = f_one.listFiles();
        if(f_temp != null) {
            for (int cnt = 0; cnt < f_temp.length; cnt++) {
                if (cnt == 0) {
                    fnl = new File(f_temp[0].getPath());
                }
            }
        }
        return fnl;
    }

    @RequiresApi(api = VERSION_CODES.N)
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
                Collections.sort(fileArray);
            }

            ArrayList<File> temp_file_list = new ArrayList<>();
            for (File files : file) {
                if (files.isFile()) {
                    if ((files.isFile()) && (!files.isHidden())) {
                        temp_file_list.add(files);
                    }
                }
            }
            Collections.sort(temp_file_list, Comparator.naturalOrder());
            fileArray.addAll(fileArray.size()-1,temp_file_list);
        }
        filesAdapter2.notifyDataSetChanged();
    }

    public static void copyF()
    {
        layout_in2.setVisibility(View.INVISIBLE);
        user = true;
        FilesAdapter2.track = user;
        orig = FilesAdapter2.get_current_selected_itm();
        Toast.makeText(paste.getContext(), "Copied "+orig,Toast.LENGTH_LONG).show();
        file_mv = new File(orig);
        Toast.makeText(paste.getContext(), "Copied "+file_mv.getPath(),Toast.LENGTH_SHORT).show();
        FilesAdapter2.files_mdls.clear();
        filesAdapter2.notifyDataSetChanged();
        show_files(new File(mpath));
        layout_in.setVisibility(View.VISIBLE);
    }
    @RequiresApi(api = VERSION_CODES.N & VERSION_CODES.Q)
    public void paste_musibat(View view)
    {
        StringJoiner joiner = new StringJoiner("/");
        final_selected_dirs = FilesAdapter2.track_my_user_move;
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
        FilesAdapter2.track_my_user_move.clear();
        FilesAdapter2.track_my_user_move.add(new File(String.valueOf(fpath)).getPath());
        final_selected_dirs.clear();
        user = false;
        FilesAdapter2.track=user;
        Toast.makeText(paste.getContext(), "Moving will"+user, Toast.LENGTH_SHORT).show();
        layout_in.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        int get_size = FilesAdapter2.last_pos.size();
//        int get_size_x = FilesAdapter2.track_my_user_move.size();
        if(get_size>0) {
            get_size -= 1;
//            get_size_x -= 1;
            FilesAdapter2.last_pos.remove(get_size);
            if(get_size >= 1)
            {
                File temp_file_path = FilesAdapter2.last_pos.get(get_size-1);
                filesAdapter2.notifyDataSetChanged();
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