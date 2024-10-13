package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.Model.BanAn;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterThongKe extends BaseAdapter {

    Context context;
    int layout;
    List<DonDat> donDatS;
    ViewHolder viewHolder;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;

    public AdapterThongKe(Context context, int layout, List<DonDat> donDatS){
        this.context = context;
        this.layout = layout;
        this.donDatS = donDatS;
        nhanVienDAO = new NhanVienDAO(context);
        banAnDAO = new BanAnDAO(context);
    }

    @Override
    public int getCount() {
        return donDatS.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatS.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDat donDat = donDatS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDat.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDat.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDat.getTongTien()+" VNĐ");
        if (donDat.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(donDat.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nhanVien.getHOTENNV());
        viewHolder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDat.getMaBan()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}
