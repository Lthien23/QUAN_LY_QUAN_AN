package com.example.quanlyquanan.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyquanan.DAO.LoaiMonDAO;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class AdapterLoaiMon extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMon> loaiMonList ;
    ViewHolder viewHolder;

    public AdapterLoaiMon(Context context, int layout, List<LoaiMon> loaiMonList){
        this.context = context;
        this.layout = layout;
        this.loaiMonList = loaiMonList;
    }

    @Override
    public int getCount() {
        return loaiMonList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonList.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_loaimon_HinhLoai = (ImageView)view.findViewById(R.id.img_loaimon_HinhLoai);
            viewHolder.txt_loaimon_TenLoai = (TextView)view.findViewById(R.id.txt_loaimon_TenLoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiMon loaiMon = loaiMonList.get(position);

        viewHolder.txt_loaimon_TenLoai.setText(loaiMon.getTenLoai());

        byte[] categoryimage = loaiMon.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.img_loaimon_HinhLoai.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        TextView txt_loaimon_TenLoai;
        ImageView img_loaimon_HinhLoai;
    }
}
