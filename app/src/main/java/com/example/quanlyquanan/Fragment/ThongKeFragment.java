package com.example.quanlyquanan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlyquanan.ThongKeActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.Adapter.AdapterThongKe;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.R;

import java.util.List;

public class ThongKeFragment extends Fragment {

    ListView lvStatistic;
    List<DonDat> donDatS;
    DonDatDAO donDatDAO;
    AdapterThongKe adapterThongKe;
    FragmentManager fragmentManager;
    int madon, manv, maban;
    String ngaydat, tongtien;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        donDatDAO = new DonDatDAO(getActivity());

        donDatS = donDatDAO.LayDSDonDat();
        adapterThongKe = new AdapterThongKe(getActivity(),R.layout.thongke,donDatS);
        lvStatistic.setAdapter(adapterThongKe);
        adapterThongKe.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon = donDatS.get(position).getMaDonDat();
                manv = donDatS.get(position).getMaNV();
                maban = donDatS.get(position).getMaBan();
                ngaydat = donDatS.get(position).getNgayDat();
                tongtien = donDatS.get(position).getTongTien();

                Intent intent = new Intent(getActivity(), ThongKeActivity.class);
                intent.putExtra("madon",madon);
                intent.putExtra("manv",manv);
                intent.putExtra("maban",maban);
                intent.putExtra("ngaydat",ngaydat);
                intent.putExtra("tongtien",tongtien);
                startActivity(intent);
            }
        });

        return view;
    }
}