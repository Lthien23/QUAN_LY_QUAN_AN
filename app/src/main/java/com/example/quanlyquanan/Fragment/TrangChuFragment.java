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

public class TrangChuFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_LoaiMon, rcv_displayhome_DonTrongNgay;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic;
    LoaiMonDAO loaiMonDAO;
    DonDatDAO donDatDAO;
    List<LoaiMon> loaiMonList;
    List<DonDat> donDatS;
    AdapterRecycleViewLoaiMon adapterRecycleViewLoaiMon;
    AdapterRecycleViewThongKe adapterRecycleViewThongKe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);

        //region Lấy dối tượng view
        rcv_displayhome_LoaiMon = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon);
        rcv_displayhome_DonTrongNgay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_DonTrongNgay);
        txt_displayhome_ViewAllCategory = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllCategory);
        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion

        //khởi tạo kết nối
        loaiMonDAO = new LoaiMonDAO(getActivity());
        donDatDAO = new DonDatDAO(getActivity());

        HienThiDSLoai();
        HienThiDonTrongNgay();

        txt_displayhome_ViewAllCategory.setOnClickListener(this);
        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void HienThiDSLoai(){
        rcv_displayhome_LoaiMon.setHasFixedSize(true);
        rcv_displayhome_LoaiMon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        loaiMonList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewLoaiMon = new AdapterRecycleViewLoaiMon(getActivity(),R.layout.loaimon,loaiMonList);
        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewLoaiMon);
        adapterRecycleViewLoaiMon.notifyDataSetChanged();
    }

    private void HienThiDonTrongNgay(){
        rcv_displayhome_DonTrongNgay.setHasFixedSize(true);
        rcv_displayhome_DonTrongNgay.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        donDatS = donDatDAO.LayDSDonDatNgay(ngaydat);
        adapterRecycleViewThongKe = new AdapterRecycleViewThongKe(getActivity(),R.layout.thongke,donDatS);
        rcv_displayhome_DonTrongNgay.setAdapter(adapterRecycleViewThongKe);
        adapterRecycleViewThongKe.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_trangchu);
        switch (id){

            case R.id.txt_displayhome_ViewAllStatistic:
                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStatistic.replace(R.id.contentView,new ThongKeFragment());
                tranDisplayStatistic.addToBackStack(null);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(R.id.nav_statistic);

                break;
            case R.id.txt_displayhome_ViewAllCategory:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new LoaiMonFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.nav_category);

                break;

        }
    }
}
