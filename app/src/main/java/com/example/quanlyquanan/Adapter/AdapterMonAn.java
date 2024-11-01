package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyquanan.Model.Mon;
import com.example.quanlyquanan.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<Mon> monList;
    Viewholder viewholder;

    //constructor
    public AdapterMonAn(Context context, int layout, List<Mon> monList){
        this.context = context;
        this.layout = layout;
        this.monList = monList;
    }

    @Override
    public int getCount() {
        return monList.size();
    }

    @Override
    public Object getItem(int position) {
        return monList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monList.get(position).getMaMon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhMon = (ImageView)view.findViewById(R.id.img_custommenu_HinhMon);
            viewholder.txt_custommenu_TenMon = (TextView) view.findViewById(R.id.txt_custommenu_TenMon);
            viewholder.txt_custommenu_TinhTrang = (TextView)view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        Mon mon = monList.get(position);
        viewholder.txt_custommenu_TenMon.setText(mon.getTenMon());
        viewholder.txt_custommenu_GiaTien.setText(mon.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của món
        if(mon.getTinhTrang().equals("true")){
            viewholder.txt_custommenu_TinhTrang.setText("Còn món");
        }else{
            viewholder.txt_custommenu_TinhTrang.setText("Hết món");
        }

        //lấy hình ảnh
        if(mon.getHinhAnh() != null){
            byte[] menuimage = mon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhMon.setImageResource(R.drawable.monanminhhoa);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_HinhMon;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien,txt_custommenu_TinhTrang;

    }
}
