package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlyquanan.Model.ThanhToan;
import com.example.quanlyquanan.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterThanhToan extends BaseAdapter {

    Context context;
    int layout;
    List<ThanhToan> thanhToanList;
    ViewHolder viewHolder;

    public AdapterThanhToan(Context context, int layout, List<ThanhToan> thanhToanList){
        this.context = context;
        this.layout = layout;
        this.thanhToanList = thanhToanList;
    }

    @Override
    public int getCount() {
        return thanhToanList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_thanhtoan_HinhMon = (CircleImageView)view.findViewById(R.id.img_thanhtoan_HinhMon);
            viewHolder.txt_thanhtoan_TenMon = (TextView)view.findViewById(R.id.txt_thanhtoan_TenMon);
            viewHolder.txt_thanhtoan_SoLuong = (TextView)view.findViewById(R.id.txt_thanhtoan_SoLuong);
            viewHolder.txt_thanhtoan_GiaTien = (TextView)view.findViewById(R.id.txt_thanhtoan_GiaTien);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ThanhToan thanhToan = thanhToanList.get(position);

        viewHolder.txt_thanhtoan_TenMon.setText(thanhToan.getTenMon());
        viewHolder.txt_thanhtoan_SoLuong.setText(String.valueOf(thanhToan.getSoLuong()));
        viewHolder.txt_thanhtoan_GiaTien.setText(String.valueOf(thanhToan.getGiaTien())+" đ");

        byte[] paymentimg = thanhToan.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.img_thanhtoan_HinhMon.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        CircleImageView img_thanhtoan_HinhMon;
        TextView txt_thanhtoan_TenMon, txt_thanhtoan_SoLuong, txt_thanhtoan_GiaTien;
    }
}
