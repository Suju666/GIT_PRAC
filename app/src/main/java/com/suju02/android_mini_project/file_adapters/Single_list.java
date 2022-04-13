package com.suju02.android_mini_project.file_adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.suju02.android_mini_project.R;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Single_list extends RecyclerView.Adapter<Single_list.FileViewHolder> {

    public Context context;
    public static ArrayList<File> files_array;
    int pos;
    public Single_list(Context context, ArrayList<File> files_array)
    { this.context = context; this.files_array = files_array; }

    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_list_front,parent,false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File files_mdl = files_array.get(position);
        holder.file_folder_name.setText(files_mdl.getName());

        if(files_mdl.isFile()) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String filelTime = df.format(files_mdl.lastModified());

            long size_in_bytes = files_mdl.length();
            String fileSize = android.text.format.Formatter.formatFileSize(context, size_in_bytes); // convert size in kb
            holder.file_folder_info.setText(filelTime + " | " + fileSize);
        }

        if (files_mdl.getName().endsWith(".mp3") || files_mdl.getName().endsWith(".m4a"))
        { holder.file_folder_img.setImageResource(R.drawable.img_2); }
        else if (files_mdl.getName().endsWith(".apk")) { holder.file_folder_img.setImageResource(R.drawable.img_1); }
        else if (files_mdl.getName().endsWith(".png") || files_mdl.getName().endsWith(".jpg") || files_mdl.getName().endsWith(".jpeg"))
        { holder.file_folder_img.setImageResource(R.drawable.colorphotos); }
        else if(files_mdl.getName().toLowerCase().endsWith(".mp4") || files_mdl.getName().toLowerCase().endsWith(".mkv"))
        { holder.file_folder_img.setImageResource(R.drawable.ic_baseline_music_video_24); }
        else if (files_mdl.getName().endsWith(".pdf")) { holder.file_folder_img.setImageResource(R.drawable.colorpdf); }
        else if (files_mdl.getName().endsWith(".doc") || files_mdl.getName().endsWith(".docx")) { holder.file_folder_img.setImageResource(R.drawable.word); }
        else if (files_mdl.getName().equals(".ppt") || files_mdl.getName().equals(".pptx")) { holder.file_folder_img.setImageResource(R.drawable.ppt); }
        else if (files_mdl.getName().endsWith(".xlsx")) { holder.file_folder_img.setImageResource(R.drawable.excel); }
        else if (files_mdl.getName().endsWith(".txt")) { holder.file_folder_img.setImageResource(R.drawable.colordocument); }
        else { holder.file_folder_img.setImageResource(R.drawable.colordocument); }

        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (files_mdl.isDirectory()) {
                        files_array.clear();
                        ShowSubFiles(files_mdl);
                    }
                }
            });
        } catch (Exception e) { Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show(); };

        holder.more_vertical_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setpos(holder.getAdapterPosition());
                return false;
            }
        });
    }

    public int getpos()
    { return pos;}

    public void setpos(int posi)
    {
        this.pos = posi;
    }

    @Override
    public int getItemCount() { return files_array.size(); }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView file_folder_img;
        TextView file_folder_name, file_folder_info;
        View layout_for_padding;
        ImageButton more_vertical_btn;

        public FileViewHolder(View adaptView) {
            super(adaptView);
            file_folder_img = adaptView.findViewById(R.id.folder_img);
            file_folder_name = adaptView.findViewById(R.id.file_folder_name);
            file_folder_info = adaptView.findViewById(R.id.file_folder_info);
            layout_for_padding = adaptView.findViewById(R.id.layout_for_padding);
            more_vertical_btn = adaptView.findViewById(R.id.more_vertical_btn);
            more_vertical_btn.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem itm1 = contextMenu.add(Menu.NONE,0,1,"Rename");
            MenuItem itm2 = contextMenu.add(Menu.NONE,1,2,"Delete");
            itm1.setOnMenuItemClickListener(menu_item);
            itm2.setOnMenuItemClickListener(menu_item);
        }

        MenuItem.OnMenuItemClickListener menu_item = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case 0:
                        View view = LayoutInflater.from(context).inflate(R.layout.rename_dialog,null);
                        EditText et = view.findViewById(R.id.nam_change);
                        et.setText(files_array.get(getpos()).getName());
                        et.selectAll();
                        TextView tv1 = view.findViewById(R.id.rename_dialog_change);
                        TextView tv2 = view.findViewById(R.id.rename_dialog_cancle);
                        AlertDialog alert = new AlertDialog.Builder(context).setView(view).create();
                        alert.show();

                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                File f4 = new File(files_array.get(getpos()).getName());
                                String before = f4.getName().toString();
                                String after = et.getText().toString();

                                File from = new File(files_array.get(getpos()).getParentFile(),before);
                                File to = new File(files_array.get(getpos()).getParentFile(),after);
                                from.renameTo(to);

                                files_array.remove(getpos());
                                files_array.add(getpos(),to);
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
                        break;

                    case 1:
                        View view2 = LayoutInflater.from(context).inflate(R.layout.delete_dialog,null);
                        TextView tv3 = view2.findViewById(R.id.delete_itm_nam);
                        tv3.setText(files_array.get(getpos()).getName());
                        TextView tv4 = view2.findViewById(R.id.delete_dialog_delete);

                        AlertDialog alert2 = new AlertDialog.Builder(context).setView(view2).create();
                        alert2.show();

                        tv4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                             File file_dlt = new File(files_array.get(getpos()).getName());
                             file_dlt.delete();
                             files_array.remove(getpos());
                             notifyDataSetChanged();
                             alert2.dismiss();
                            }
                        });
                        break;

                    default:
                        Toast.makeText(context, "DEFAULT", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        };
    }

    public void ShowSubFiles(File filePath) {
        File files[] = filePath.listFiles();
        if (files != null) {
            for (File file : files)
            { if (file.isDirectory()) { files_array.add(file); } if(file.isFile()) { files_array.add(file); } }
        }
    }
}