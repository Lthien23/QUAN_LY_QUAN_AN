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

            viewHolder.txt_thongke_MaDon = (TextView)view.findViewById(R.id.txt_thongke_MaDon);
            viewHolder.txt_thongke_NgayDat = (TextView)view.findViewById(R.id.txt_thongke_NgayDat);
            viewHolder.txt_thongke_TenNV = (TextView)view.findViewById(R.id.txt_thongke_TenNV);
            viewHolder.txt_thongke_TongTien = (TextView)view.findViewById(R.id.txt_thongke_TongTien);
            viewHolder.txt_thongke_TrangThai = (TextView)view.findViewById(R.id.txt_thongke_TrangThai);
            viewHolder.txt_thongke_BanDat = (TextView)view.findViewById(R.id.txt_thongke_BanDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDat donDat = donDatS.get(position);

        viewHolder.txt_thongke_MaDon.setText("Mã đơn: "+donDat.getMaDonDat());
        viewHolder.txt_thongke_NgayDat.setText(donDat.getNgayDat());
        viewHolder.txt_thongke_TongTien.setText(donDat.getTongTien()+" VNĐ");
        if (donDat.getTinhTrang().equals("true"))
        {
            viewHolder.txt_thongke_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_thongke_TrangThai.setText("Chưa thanh toán");
        }
        NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(donDat.getMaNV());
        viewHolder.txt_thongke_TenNV.setText(nhanVien.getHOTENNV());
        viewHolder.txt_thongke_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDat.getMaBan()));

        return view;
    }
    public class ViewHolder{
        TextView txt_thongke_MaDon, txt_thongke_NgayDat, txt_thongke_TenNV
                ,txt_thongke_TongTien,txt_thongke_TrangThai, txt_thongke_BanDat;

    }
}
