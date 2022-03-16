package com.suju02.android_mini_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    public static Context context;
    public static ArrayList<File> files_mdls;

    public FilesAdapter(Context context, ArrayList<File> files_mdls) { // getting arraylist from files.java by creating object of filesadapter.java
        this.context = context; this.files_mdls = files_mdls;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // inflater uses context and .xml file for each item in recycler view
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent,false);
        return new FileViewHolder(view); // passing view to fileviewholder constructor
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        File files_mdl = files_mdls.get(position);
        holder.file_folder_name.setText(files_mdl.getName());

        if (files_mdl.isDirectory()) { int items = countItems(files_mdl); holder.file_folder_info.setText(items + " Files"); }
        else {
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String filelTime = df.format(files_mdl.lastModified());

            long size_in_bytes = files_mdl.length(); // taking file lenth in long types
            String fileSize = android.text.format.Formatter.formatFileSize(context, size_in_bytes); // convert size in kb
            holder.file_folder_info.setText(filelTime + " | " + fileSize);
        }

        if (files_mdl.getName().endsWith(".mp3") || files_mdl.getName().endsWith(".m4a")) {
            holder.file_folder_img.setImageResource(R.drawable.itunes);
        }
        else if (files_mdl.getName().endsWith(".apk")) { holder.file_folder_img.setImageResource(R.drawable.img_1); }
        else if (files_mdl.getName().endsWith(".png") || files_mdl.getName().endsWith(".jpg") || files_mdl.getName().endsWith(".jpeg"))
        { holder.file_folder_img.setImageDrawable(null); }
        else if (files_mdl.getName().endsWith(".pdf")) { holder.file_folder_img.setImageResource(R.drawable.colorpdf); }
        else if (files_mdl.getName().endsWith(".doc") || files_mdl.getName().endsWith(".docx")) { holder.file_folder_img.setImageResource(R.drawable.word); }
        else if (files_mdl.getName().endsWith(".xlsx")) { holder.file_folder_img.setImageResource(R.drawable.excel); }
        else if (files_mdl.getName().endsWith(".txt")) { holder.file_folder_img.setImageResource(R.drawable.colordocument); }
        else if (files_mdl.isDirectory()) { holder.file_folder_img.setImageResource(R.drawable.ic_baseline_folder_24); }
        else { holder.file_folder_img.setImageResource(R.drawable.colordocument); }

        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (files_mdl.isDirectory()) {
                // fileModels.clone(); to save root file and folders recycler view after click on back button. // PENDING
                files_mdls.clear(); // clearing arraylist
                ShowSubFiles(files_mdl); // for sub filen and direcotry
                }
            }
            });
        } catch (Exception e) { Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show(); }
    }

    @Override
    public int getItemCount() { return files_mdls.size(); }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        ImageView file_folder_img;
        TextView file_folder_name, file_folder_info;
        View layout_for_padding;
        ConstraintLayout ct;
        ImageButton more_vertical_btn;

        public FileViewHolder(View adaptView) {
            super(adaptView);
            ct = adaptView.findViewById(R.id.clickable_files);
            file_folder_img = adaptView.findViewById(R.id.folder_img);
            file_folder_name = adaptView.findViewById(R.id.file_folder_name);
            file_folder_info = adaptView.findViewById(R.id.file_folder_info);
            layout_for_padding = adaptView.findViewById(R.id.layout_for_padding);
            more_vertical_btn = adaptView.findViewById(R.id.more_vertical_btn);
        }
    }

    public void ShowSubFiles(File filePath) {
        File files[] = filePath.listFiles();
        if (files != null) {
            for (File file : files)
            { if (file.isDirectory()) { files_mdls.add(file); } if(file.isFile()) { files_mdls.add(file); } }
        }
    }

    public int countItems(File folder) { // counting files list for folder
        int cnt = 0; File[] sub_folder_counting = folder.listFiles();
        for (int i = 0; i < sub_folder_counting.length; i++) { cnt++; }
        return cnt;
    }
}