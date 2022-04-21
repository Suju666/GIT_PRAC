package com.suju02.android_mini_project.file_adapters;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.suju02.android_mini_project.FileList;
import com.suju02.android_mini_project.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilesAdapter2 extends RecyclerView.Adapter<FilesAdapter2.FileViewHolder>  {

    public static Context context;
    public static ArrayList<File> files_mdls;
    public static ArrayList<File> last_pos;
    public static ArrayList<String> track_my_user_move;
    public static Boolean track=false;
    static int pos;
    public int rename_time_pos;
    public static int CNT=0;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public FilesAdapter2(Context context, ArrayList<File> files_mdls) { // getting arraylist from files.java by creating object of filesadapter.java
        this.context = context; this.files_mdls = files_mdls; last_pos = new ArrayList<>(); track_my_user_move = new ArrayList<>(); track_my_user_move.add(new File(Environment.getStorageDirectory().getPath()).getPath());
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent,false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        rename_time_pos = position;
        File files_mdl = files_mdls.get(position);
        holder.file_folder_name.setText(files_mdl.getName());

        if (files_mdl.isDirectory()) { int items = countItems(files_mdl); int sub_folder = countFolder(files_mdl); holder.file_folder_info.setText(sub_folder + " Folders | " + items + " Files"); }
        else {
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String filelTime = df.format(files_mdl.lastModified());

            long size_in_bytes = files_mdl.length();
            String fileSize = android.text.format.Formatter.formatFileSize(context, size_in_bytes); // convert size in kb
            holder.file_folder_info.setText(filelTime + " | " + fileSize);
        }

        if (files_mdl.getName().endsWith(".mp3") || files_mdl.getName().endsWith(".m4a"))
        { holder.file_folder_img.setImageResource(R.drawable.img_2);}
        else if(files_mdl.getName().toLowerCase().endsWith(".mkv") || files_mdl.getName().endsWith(".mp4"))
        { holder.file_folder_img.setImageResource(R.drawable.ic_baseline_music_video_24); }
        else if (files_mdl.getName().endsWith(".apk")) { holder.file_folder_img.setImageResource(R.drawable.img_1); }
        else if (files_mdl.getName().endsWith(".png") || files_mdl.getName().endsWith(".jpg") || files_mdl.getName().endsWith(".jpeg"))
        { holder.file_folder_img.setImageResource(R.drawable.picture); }
        else if (files_mdl.getName().endsWith(".pdf")) { holder.file_folder_img.setImageResource(R.drawable.colorpdf); }
        else if (files_mdl.getName().endsWith(".doc") || files_mdl.getName().endsWith(".docx")) { holder.file_folder_img.setImageResource(R.drawable.word); }
        else if(files_mdl.getName().endsWith(".ppt") || files_mdl.getName().endsWith(".pptx")) { holder.file_folder_img.setImageResource(R.drawable.ppt);}
        else if (files_mdl.getName().endsWith(".xlsx")) { holder.file_folder_img.setImageResource(R.drawable.excel); }
        else if (files_mdl.getName().endsWith(".txt")) { holder.file_folder_img.setImageResource(R.drawable.colordocument); }
        else if (files_mdl.isDirectory()) { holder.file_folder_img.setImageResource(R.drawable.ic_baseline_folder_24); }
        else { holder.file_folder_img.setImageResource(R.drawable.colordocument); }

        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (files_mdl.isDirectory()) {
                        Toast.makeText( context, "Folder " + files_mdl.getName(), Toast.LENGTH_SHORT).show();
                        last_pos.add(files_mdl);
                        if (track == true)
                        { track_my_user_move.add(files_mdl.getName()); Toast.makeText(context, "added"+files_mdl.getName(), Toast.LENGTH_SHORT).show();}
                        files_mdls.clear();
                        ShowSubFiles(files_mdl);
                    }
                    notifyDataSetChanged();
                }
            });
        } catch (Exception e) { Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show(); }

        holder.more_vertical_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setpos(holder.getPosition());
                return false;
            }
        });
    }

    public static String get_current_selected_itm()
    {
        FileList.copy_file = new File(String.valueOf(files_mdls.get(getpos())));
        String copy_file_path = FileList.copy_file.getPath();
        return copy_file_path;
    }

    public static int getpos() { return pos;}

    public void setpos(int posi) { this.pos = posi;}

    @Override
    public int getItemCount() {
        return files_mdls.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView file_folder_img;
        TextView file_folder_name, file_folder_info;
        View layout_for_padding;
        CardView girgid_itm;
        ImageButton more_vertical_btn;

        public FileViewHolder(View adaptView) {
            super(adaptView);
            girgid_itm = adaptView.findViewById(R.id.girgid);
            file_folder_img = adaptView.findViewById(R.id.folder_img);
            file_folder_name = adaptView.findViewById(R.id.file_folder_name);
            file_folder_info = adaptView.findViewById(R.id.file_folder_info);
            layout_for_padding = adaptView.findViewById(R.id.layout_for_padding);
            more_vertical_btn = adaptView.findViewById(R.id.more_vertical_btn);
            more_vertical_btn.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem itm1 = contextMenu.add(contextMenu.NONE,0,1,"Rename");
            MenuItem itm2 = contextMenu.add(contextMenu.NONE,1,2,"Delete");
            MenuItem itm3 = contextMenu.add(contextMenu.NONE,2,3,"Copy");
            itm1.setOnMenuItemClickListener(menuitem);
            itm2.setOnMenuItemClickListener(menuitem);
            itm3.setOnMenuItemClickListener(menuitem);
        }

        MenuItem.OnMenuItemClickListener menuitem = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case 0:
                        prepare_rename_dialog();
                        break;

                    case 1:
                        prepare_delete_dialog();
                        break;

                    case 2:
                        FileList.copyF();

                        break;

                    default:
                        Toast.makeText(context,"DEFAULT",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        };

        public void prepare_rename_dialog()
        {
            View view = LayoutInflater.from(context).inflate(R.layout.rename_dialog,null);
            EditText et = view.findViewById(R.id.nam_change);
            et.setText(files_mdls.get(getpos()).getName().toString());
            et.selectAll();
            TextView tv1 = view.findViewById(R.id.rename_dialog_change);
            TextView tv2 = view.findViewById(R.id.rename_dialog_cancle);

            AlertDialog alert= new AlertDialog.Builder(context).setView(view).create();
            alert.show();
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File get_file = new File(String.valueOf(files_mdls.get(getpos())));
                    String before = get_file.getName().toString();
                    String after = et.getEditableText().toString();
                    File from = new File(files_mdls.get(getpos()).getParentFile(),before);
                    File to = new File(files_mdls.get(getpos()).getParentFile(),after);
                    from.renameTo(to);

                    files_mdls.remove(getpos());
                    files_mdls.add(getpos(),to);
                    notifyDataSetChanged();
                    alert.dismiss();
                }
            });

            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();
                }
            });
        }

        public void prepare_delete_dialog()
        {
            View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog,null);
            TextView tv1 = view.findViewById(R.id.delete_itm_nam);
            tv1.setText(files_mdls.get(getpos()).getName());
            TextView tv2 = view.findViewById(R.id.delete_dialog_delete);

            AlertDialog alert = new AlertDialog.Builder(context).setView(view).create();
            alert.show();

            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File f = new File(files_mdls.get(getpos()).getPath());
                    f.delete();
                    files_mdls.remove(getpos());
                    notifyDataSetChanged();
                    alert.dismiss();
                }
            });
        }
    }

    public void ShowSubFiles(File filePath) {
        File files[] = filePath.listFiles();
        if (files != null) {
            for (File file : files)
            { if (file.isDirectory()) { files_mdls.add(file); } if(file.isFile()) { files_mdls.add(file); } }
        }notifyDataSetChanged();
    }

    public int countItems(File folder) { // counting files list for folder
        int cnt = 0; File[] files = folder.listFiles();
        if(files != null) {
            for (File single : files) {
                if (single.isFile()) {
                    cnt++;
                }
            }
        }
        return cnt++;
    }

    public int countFolder(File cnt_sub_folder)
    {
        int cnt = 0;
        File[] sub_folder = cnt_sub_folder.listFiles();
        if(sub_folder != null) {
            for (File single : sub_folder) {
                if (single.isDirectory()) {
                    cnt++;
                }
            }
        }
        return cnt++;
    }
}