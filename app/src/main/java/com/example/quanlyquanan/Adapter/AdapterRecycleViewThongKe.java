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

// Lớp AdapterRecycleViewThongKe kế thừa từ RecyclerView.Adapter
public class AdapterRecycleViewThongKe extends RecyclerView.Adapter<AdapterRecycleViewThongKe.ViewHolder> {

    Context context; // Context của Activity hoặc Fragment để tạo các view
    int layout; // Layout của mỗi item trong RecyclerView
    List<DonDat> donDatList; // Danh sách các đơn đặt hàng để hiển thị
    NhanVienDAO nhanVienDAO; // Đối tượng DAO để lấy thông tin nhân viên
    BanAnDAO banAnDAO; // Đối tượng DAO để lấy thông tin bàn

    // Constructor để khởi tạo adapter với context, layout và danh sách đơn đặt
    public AdapterRecycleViewThongKe(Context context, int layout, List<DonDat> donDatList) {
        this.context = context;
        this.layout = layout;
        this.donDatList = donDatList;
        nhanVienDAO = new NhanVienDAO(context); // Khởi tạo đối tượng DAO cho nhân viên
        banAnDAO = new BanAnDAO(context); // Khởi tạo đối tượng DAO cho bàn ăn
    }

    // Phương thức này được gọi khi RecyclerView cần tạo ViewHolder cho item mới
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Tạo view từ layout resource
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view); // Trả về một đối tượng ViewHolder
    }

    // Phương thức này được gọi để gắn dữ liệu vào từng item của RecyclerView
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DonDat donDat = donDatList.get(position); // Lấy đơn đặt hàng từ danh sách tại vị trí hiện tại
        holder.txt_thongke_MaDon.setText("Mã đơn: " + donDat.getMaDonDat()); // Đặt mã đơn vào TextView
        holder.txt_thongke_NgayDat.setText(donDat.getNgayDat()); // Đặt ngày đặt vào TextView

        // Kiểm tra xem tổng tiền có phải là 0 hay không, nếu có thì ẩn TextView tổng tiền
        if (donDat.getTongTien().equals("0")) {
            holder.txt_thongke_TongTien.setVisibility(View.INVISIBLE);
        } else {
            holder.txt_thongke_TongTien.setVisibility(View.VISIBLE);
        }

        // Kiểm tra trạng thái thanh toán và hiển thị "Đã thanh toán" hoặc "Chưa thanh toán"
        if (donDat.getTinhTrang().equals("true")) {
            holder.txt_thongke_TrangThai.setText("Đã thanh toán");
        } else {
            holder.txt_thongke_TrangThai.setText("Chưa thanh toán");
        }

        // Lấy thông tin nhân viên từ mã nhân viên trong đơn đặt hàng và hiển thị tên nhân viên
        NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(donDat.getMaNV());
        holder.txt_thongke_TenNV.setText(nhanVien.getHOTENNV());

        // Lấy tên bàn từ mã bàn trong đơn đặt hàng và hiển thị
        holder.txt_thongke_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDat.getMaBan()));
    }

    // Phương thức này trả về số lượng item trong RecyclerView
    @Override
    public int getItemCount() {
        return donDatList.size(); // Trả về số lượng đơn đặt trong danh sách
    }

    // Lớp ViewHolder giúp lưu trữ các view của mỗi item, để tối ưu hóa việc tìm kiếm view
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Các TextView để hiển thị thông tin đơn đặt
        TextView txt_thongke_MaDon, txt_thongke_NgayDat, txt_thongke_TenNV,
                txt_thongke_BanDat, txt_thongke_TongTien, txt_thongke_TrangThai;

        // Constructor của ViewHolder, tìm kiếm các view trong layout của item
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
