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

// Lớp AdapterRecycleViewLoaiMon kế thừa từ RecyclerView.Adapter
public class AdapterRecycleViewLoaiMon extends RecyclerView.Adapter<AdapterRecycleViewLoaiMon.ViewHolder>{

    Context context; // Context của Activity hoặc Fragment để tạo các view
    int layout; // Layout của mỗi item trong RecyclerView
    List<LoaiMon> loaiMonList; // Danh sách các loại món để hiển thị

    // Constructor để khởi tạo adapter với context, layout và danh sách loại món
    public AdapterRecycleViewLoaiMon(Context context, int layout, List<LoaiMon> loaiMonList){
        this.context = context;
        this.layout = layout;
        this.loaiMonList = loaiMonList;
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
    public void onBindViewHolder(AdapterRecycleViewLoaiMon.ViewHolder holder, int position) {
        LoaiMon loaiMon = loaiMonList.get(position); // Lấy loại món từ danh sách ở vị trí hiện tại
        holder.txt_loaimon_TenLoai.setText(loaiMon.getTenLoai()); // Đặt tên loại món vào TextView

        // Chuyển đổi mảng byte hình ảnh của loại món thành Bitmap để hiển thị lên ImageView
        byte[] categoryimage = loaiMon.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage, 0, categoryimage.length);
        holder.img_loaimon_HinhLoai.setImageBitmap(bitmap); // Đặt hình ảnh vào ImageView
    }

    // Phương thức này trả về số lượng item trong RecyclerView
    @Override
    public int getItemCount() {
        return loaiMonList.size(); // Trả về số lượng loại món trong danh sách
    }

    // Lớp ViewHolder giúp lưu trữ các view của mỗi item, để tối ưu hóa việc tìm kiếm view
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_loaimon_TenLoai; // TextView hiển thị tên loại món
        ImageView img_loaimon_HinhLoai; // ImageView hiển thị hình ảnh của loại món

        // Constructor của ViewHolder, tìm kiếm các view trong layout của item
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_loaimon_TenLoai = itemView.findViewById(R.id.txt_loaimon_TenLoai); // Tìm kiếm TextView trong layout
            img_loaimon_HinhLoai = itemView.findViewById(R.id.img_loaimon_HinhLoai); // Tìm kiếm ImageView trong layout
        }
    }

}
