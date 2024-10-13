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

            viewHolder.img_customstaff_HinhNV = (ImageView)view.findViewById(R.id.img_customstaff_HinhNV);
            viewHolder.txt_customstaff_TenNV = (TextView)view.findViewById(R.id.txt_customstaff_TenNV);
            viewHolder.txt_customstaff_TenQuyen = (TextView)view.findViewById(R.id.txt_customstaff_TenQuyen);
            viewHolder.txt_customstaff_SDT = (TextView)view.findViewById(R.id.txt_customstaff_SDT);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVien nhanVien = nhanVienS.get(position);

        viewHolder.txt_customstaff_TenNV.setText(nhanVien.getHOTENNV());
        viewHolder.txt_customstaff_TenQuyen.setText(quyenDAO.LayTenQuyenTheoMa(nhanVien.getMAQUYEN()));
        viewHolder.txt_customstaff_SDT.setText(nhanVien.getSDT());
        viewHolder.txt_customstaff_Email.setText(nhanVien.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_HinhNV;
        TextView txt_customstaff_TenNV, txt_customstaff_TenQuyen,txt_customstaff_SDT, txt_customstaff_Email;
    }
}
