package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyquanan.DAO.QuyenDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import java.util.List;

public class AdapterNhanVien extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVien> nhanVienS;
    ViewHolder viewHolder;
    QuyenDAO quyenDAO;

    public AdapterNhanVien(Context context, int layout, List<NhanVien> nhanVienS){
        this.context = context;
        this.layout = layout;
        this.nhanVienS = nhanVienS;
        quyenDAO = new QuyenDAO(context);
    }

    @Override
    public int getCount() {
        return nhanVienS.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienS.get(position).getMANV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_nhanvien_HinhNV = (ImageView)view.findViewById(R.id.img_nhanvien_HinhNV);
            viewHolder.txt_nhanvien_TenNV = (TextView)view.findViewById(R.id.txt_nhanvien_TenNV);
            viewHolder.txt_nhanvien_TenQuyen = (TextView)view.findViewById(R.id.txt_nhanvien_TenQuyen);
            viewHolder.txt_nhanvien_SDT = (TextView)view.findViewById(R.id.txt_nhanvien_SDT);
            viewHolder.txt_nhanvien_tendn = (TextView)view.findViewById(R.id.txt_nhanvien_tendn);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVien nhanVien = nhanVienS.get(position);

        viewHolder.txt_nhanvien_TenNV.setText(nhanVien.getHOTENNV());
        viewHolder.txt_nhanvien_TenQuyen.setText(quyenDAO.LayTenQuyenTheoMa(nhanVien.getMAQUYEN()));
        viewHolder.txt_nhanvien_SDT.setText(nhanVien.getSDT());
        viewHolder.txt_nhanvien_tendn.setText(nhanVien.getTENDN());

        return view;
    }

    public class ViewHolder{
        ImageView img_nhanvien_HinhNV;
        TextView txt_nhanvien_TenNV, txt_nhanvien_TenQuyen,txt_nhanvien_SDT, txt_nhanvien_tendn;
    }
}
