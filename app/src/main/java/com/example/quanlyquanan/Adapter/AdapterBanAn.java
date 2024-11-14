package com.example.quanlyquanan.Adapter;

// Các import cần thiết
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.ThanhToanActivity;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.Model.BanAn;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Fragment.LoaiMonFragment;
import com.example.quanlyquanan.Fragment.MonAnFragment;
import com.example.quanlyquanan.Fragment.BanAnFragment;
import com.example.quanlyquanan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

// Lớp AdapterBanAn kế thừa từ BaseAdapter và thực hiện OnClickListener để xử lý sự kiện nhấn vào các item
public class AdapterBanAn extends BaseAdapter implements View.OnClickListener {

    // Khai báo các biến cần thiết
    Context context; // context của Activity gọi adapter này
    int layout; // layout của mỗi item
    List<BanAn> banAnList; // danh sách các bàn ăn
    ViewHolder viewHolder; // view holder để lưu các view của mỗi item
    BanAnDAO banAnDAO; // đối tượng truy xuất dữ liệu bàn ăn
    DonDatDAO donDatDAO; // đối tượng truy xuất dữ liệu đơn đặt món
    FragmentManager fragmentManager; // quản lý fragment

    // Constructor để khởi tạo adapter với context, layout và danh sách bàn ăn
    public AdapterBanAn(Context context, int layout, List<BanAn> banAnList) {
        this.context = context;
        this.layout = layout;
        this.banAnList = banAnList;
        banAnDAO = new BanAnDAO(context); // Khởi tạo đối tượng BanAnDAO
        donDatDAO = new DonDatDAO(context); // Khởi tạo đối tượng DonDatDAO
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager(); // Lấy FragmentManager từ TrangChuActivity
    }

    @Override
    public int getCount() {
        return banAnList.size(); // Trả về số lượng bàn ăn
    }

    @Override
    public Object getItem(int position) {
        return banAnList.get(position); // Trả về bàn ăn ở vị trí được chỉ định
    }

    @Override
    public long getItemId(int position) {
        return banAnList.get(position).getMaBan(); // Trả về ID của bàn ăn
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) { // Kiểm tra nếu view chưa được tạo
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Tạo inflater để chuyển đổi layout XML thành view
            viewHolder = new ViewHolder(); // Tạo đối tượng ViewHolder
            view = inflater.inflate(layout, parent, false); // Gán layout cho mỗi item view

            // Khởi tạo các view trong ViewHolder
            viewHolder.imgBanAn = view.findViewById(R.id.img_banan_BanAn); // Hình ảnh của bàn ăn
            viewHolder.imgGoiMon = view.findViewById(R.id.img_banan_GoiMon); // Hình ảnh nút gọi món
            viewHolder.imgThanhToan = view.findViewById(R.id.img_banan_ThanhToan); // Hình ảnh nút thanh toán
            viewHolder.txtTenBanAn = view.findViewById(R.id.txt_banan_TenBanAn); // Tên của bàn ăn

            view.setTag(viewHolder); // Lưu viewHolder vào view
        } else {
            viewHolder = (ViewHolder) view.getTag(); // Lấy viewHolder đã lưu từ view
        }

        BanAn banAn = banAnList.get(position); // Lấy thông tin bàn ăn ở vị trí hiện tại

        // Lấy trạng thái bàn ăn từ database để xác định hình ảnh của bàn
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAn.getMaBan());

        // Đặt hình ảnh cho bàn ăn theo tình trạng
        if (kttinhtrang.equals("true")) {
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_person_24); // Hình ảnh khi bàn đã có người ngồi
        } else {
            viewHolder.imgBanAn.setImageResource(R.drawable.img); // Hình ảnh khi bàn trống
        }

        viewHolder.txtTenBanAn.setText(banAn.getTenBan()); // Đặt tên bàn ăn
        viewHolder.imgBanAn.setTag(position); // Lưu vị trí của bàn trong imgBanAn để sử dụng khi xử lý sự kiện

        // Thiết lập sự kiện click cho các hình ảnh
        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId(); // Lấy ID của view vừa được nhấn
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag(); // Lấy viewHolder của item hiện tại
        int vitri1 = (int) viewHolder.imgBanAn.getTag(); // Lấy vị trí của bàn từ view
        int maban = banAnList.get(vitri1).getMaBan(); // Lấy mã bàn ăn
        String tenban = banAnList.get(vitri1).getTenBan(); // Lấy tên bàn ăn
        Calendar calendar = Calendar.getInstance(); // Lấy ngày giờ hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Định dạng ngày tháng
        String ngaydat = dateFormat.format(calendar.getTime()); // Chuyển đổi thành chuỗi ngày

        switch (id) {
            case R.id.img_banan_BanAn:
                int vitri = (int) v.getTag(); // Lấy vị trí của bàn khi nhấn vào hình ảnh bàn ăn
                banAnList.get(vitri).setDuocChon(true); // Đánh dấu bàn được chọn
                break;
            case R.id.img_banan_GoiMon:
                Intent getIHome = ((TrangChuActivity) context).getIntent(); // Lấy intent từ TrangChuActivity
                int manv = getIHome.getIntExtra("manv", 0); // Lấy mã nhân viên từ intent
                // Kiểm tra tình trạng bàn, nếu bàn trống thì thêm đơn đặt và cập nhật tình trạng bàn
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if (tinhtrang.equals("false")) {
                    DonDat donDat = new DonDat();
                    donDat.setMaBan(maban);
                    donDat.setMaNV(manv);
                    donDat.setNgayDat(ngaydat);
                    donDat.setTinhTrang("false");
                    donDat.setTongTien("0");

                    long ktra = donDatDAO.ThemDonDat(donDat); // Thêm đơn đặt vào database
                    banAnDAO.CapNhatTinhTrangBan(maban, "true"); // Cập nhật tình trạng bàn

                    if (ktra == 0) { // Kiểm tra nếu thêm thất bại
                        Toast.makeText(context, context.getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                // Chuyển sang fragment Loại món để chọn món ăn cho bàn
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                LoaiMonFragment displayCategoryFragment = new LoaiMonFragment();
                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban", maban); // Truyền mã bàn qua fragment
                displayCategoryFragment.setArguments(bDataCategory);
                transaction.replace(R.id.contentView, displayCategoryFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;

            case R.id.img_banan_ThanhToan:
                // Chuyển dữ liệu qua ThanhToanActivity để thực hiện thanh toán cho bàn
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban", maban);
                iThanhToan.putExtra("tenban", tenban);
                ((Activity) context).startActivityForResult(iThanhToan, 55);
                break;
        }
    }


    // Lớp ViewHolder giúp tối ưu hiệu suất bằng cách lưu trữ các view của item
    public class ViewHolder {
        ImageView imgBanAn, imgGoiMon, imgThanhToan; // Hình ảnh của bàn ăn, nút gọi món, và nút thanh toán
        TextView txtTenBanAn; // Tên của bàn ăn
    }
}
