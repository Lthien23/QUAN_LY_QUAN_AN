package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.R;

import java.util.List;

public class AdapterRecycleViewLoaiMon extends RecyclerView.Adapter<AdapterRecycleViewLoaiMon.ViewHolder>{

    Context context;
    int layout;
    List<LoaiMon> loaiMonList;

    public AdapterRecycleViewLoaiMon(Context context,int layout, List<LoaiMon> loaiMonList){
        this.context = context;
        this.layout = layout;
        this.loaiMonList = loaiMonList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewLoaiMon.ViewHolder holder, int position) {
        LoaiMon loaiMon = loaiMonList.get(position);
        holder.txt_customcategory_TenLoai.setText(loaiMon.getTenLoai());
        byte[] categoryimage = loaiMon.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        holder.img_customcategory_HinhLoai.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return loaiMonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customcategory_TenLoai = itemView.findViewById(R.id.txt_customcategory_TenLoai);
            img_customcategory_HinhLoai = itemView.findViewById(R.id.img_customcategory_HinhLoai);
        }
    }

}
