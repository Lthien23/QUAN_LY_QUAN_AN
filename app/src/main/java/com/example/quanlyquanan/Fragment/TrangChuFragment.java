package com.example.quanlyquanan.Fragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.example.quanlyquanan.ThemLoaiMonActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.Adapter.AdapterLoaiMon;
import com.example.quanlyquanan.Adapter.AdapterRecycleViewLoaiMon;
import com.example.quanlyquanan.Adapter.AdapterRecycleViewThongKe;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.DAO.LoaiMonDAO;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Fragment `TrangChuFragment` hiển thị giao diện Trang Chủ với các thông tin:
 * - Danh sách Loại Món
 * - Chuyển đến danh sách chi tiết các Loại Món
 */
public class TrangChuFragment extends Fragment implements View.OnClickListener {

    //region Khai báo biến
    RecyclerView rcv_displayhome_LoaiMon; // RecyclerView hiển thị danh sách Loại Món
    TextView txt_trangchuFragment_Xemchitietloaimon; // TextView chuyển đến danh sách chi tiết Loại Món
    LoaiMonDAO loaiMonDAO; // DAO để thao tác dữ liệu Loại Món
    DonDatDAO donDatDAO; // DAO để thao tác dữ liệu Đơn Đặt
    List<LoaiMon> loaiMonList; // Danh sách Loại Món
    List<DonDat> donDatS; // Danh sách Đơn Đặt
    AdapterRecycleViewLoaiMon adapterRecycleViewLoaiMon; // Adapter quản lý hiển thị danh sách Loại Món
    AdapterRecycleViewThongKe adapterRecycleViewThongKe; // Adapter quản lý thống kê
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        // Đặt tiêu đề cho ActionBar trong TrangChuActivity
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle("Trang chủ");

        // Cho phép Fragment có thể thêm các menu item vào Toolbar
        setHasOptionsMenu(true);

        //region Lấy các thành phần giao diện từ layout
        rcv_displayhome_LoaiMon = (RecyclerView) view.findViewById(R.id.rcv_trangchuFragment_LoaiMon); // RecyclerView
        txt_trangchuFragment_Xemchitietloaimon = (TextView) view.findViewById(R.id.txt_trangchuFragment_Xemchitietloaimon); // TextView
        //endregion

        // Khởi tạo các đối tượng DAO để truy cập dữ liệu
        loaiMonDAO = new LoaiMonDAO(getActivity()); // Khởi tạo DAO Loại Món
        donDatDAO = new DonDatDAO(getActivity()); // Khởi tạo DAO Đơn Đặt

        // Hiển thị danh sách Loại Món trên giao diện
        HienThiDSLoai();

        // Đăng ký sự kiện click cho TextView "Xem chi tiết"
        txt_trangchuFragment_Xemchitietloaimon.setOnClickListener(this);

        return view; // Trả về View đã được tạo
    }

    /**
     * Hiển thị danh sách Loại Món lên RecyclerView.
     */
    private void HienThiDSLoai() {
        rcv_displayhome_LoaiMon.setHasFixedSize(true); // Tăng hiệu suất cho RecyclerView nếu kích thước không thay đổi
        rcv_displayhome_LoaiMon.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        ); // Đặt layout cho RecyclerView theo hướng dọc

        loaiMonList = loaiMonDAO.LayDSLoaiMon(); // Lấy danh sách Loại Món từ cơ sở dữ liệu
        adapterRecycleViewLoaiMon = new AdapterRecycleViewLoaiMon(
                getActivity(), R.layout.loaimon, loaiMonList
        ); // Tạo Adapter với danh sách Loại Món
        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewLoaiMon); // Gắn Adapter vào RecyclerView
        adapterRecycleViewLoaiMon.notifyDataSetChanged(); // Thông báo Adapter cập nhật dữ liệu
    }

    @Override
    public void onClick(View v) {
        int id = v.getId(); // Lấy ID của View được click

        // Truy cập NavigationView trong TrangChuActivity
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view_trangchu);

        switch (id) {
            case R.id.txt_trangchuFragment_Xemchitietloaimon: // Khi click vào TextView "Xem chi tiết"
                FragmentTransaction tranDisplayCategory = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction(); // Bắt đầu giao dịch Fragment cho phép bạn thêm, thay thế, xóa, hoặc thực hiện các thay đổi khác trên các Fragment trong ứng dụng của mình.

                tranDisplayCategory.replace(R.id.contentView, new LoaiMonFragment()); // Thay thế Fragment hiện tại bằng LoaiMonFragment
                tranDisplayCategory.addToBackStack(null); // Thêm giao dịch vào BackStack để có thể quay lại
                tranDisplayCategory.commit(); // Hoàn thành giao dịch Fragment
                break;
        }
    }
}
