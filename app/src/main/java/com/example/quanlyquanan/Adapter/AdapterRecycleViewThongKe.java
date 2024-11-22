package com.example.quanlyquanan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import java.util.List;

public class AdapterRecycleViewThongKe extends RecyclerView.Adapter<AdapterRecycleViewThongKe.ViewHolder> {

    Context context;
    int layout;
    List<DonDat> donDatList;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;

    public AdapterRecycleViewThongKe(Context context, int layout, List<DonDat> donDatList) {
        this.context = context;
        this.layout = layout;
        this.donDatList = donDatList;
        nhanVienDAO = new NhanVienDAO(context);
        banAnDAO = new BanAnDAO(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DonDat donDat = donDatList.get(position);
        holder.txt_thongke_MaDon.setText("Mã đơn: " + donDat.getMaDonDat());
        holder.txt_thongke_NgayDat.setText(donDat.getNgayDat());

        if (donDat.getTongTien().equals("0")) {
            holder.txt_thongke_TongTien.setVisibility(View.INVISIBLE);
        } else {
            holder.txt_thongke_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDat.getTinhTrang().equals("true")) {
            holder.txt_thongke_TrangThai.setText("Đã thanh toán");
        } else {
            holder.txt_thongke_TrangThai.setText("Chưa thanh toán");
        }

        NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(donDat.getMaNV());
        holder.txt_thongke_TenNV.setText(nhanVien.getHOTENNV());

        holder.txt_thongke_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDat.getMaBan()));
    }

    @Override
    public int getItemCount() {
        return donDatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_thongke_MaDon, txt_thongke_NgayDat, txt_thongke_TenNV,
                txt_thongke_BanDat, txt_thongke_TongTien, txt_thongke_TrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Tìm kiếm các TextView trong layout của mỗi item
            txt_thongke_MaDon = itemView.findViewById(R.id.txt_thongke_MaDon);
            txt_thongke_NgayDat = itemView.findViewById(R.id.txt_thongke_NgayDat);
            txt_thongke_TenNV = itemView.findViewById(R.id.txt_thongke_TenNV);
            txt_thongke_BanDat = itemView.findViewById(R.id.txt_thongke_BanDat);
            txt_thongke_TongTien = itemView.findViewById(R.id.txt_thongke_TongTien);
            txt_thongke_TrangThai = itemView.findViewById(R.id.txt_thongke_TrangThai);
        }
    }
}
