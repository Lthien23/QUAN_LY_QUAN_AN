package com.example.quanlyquanan.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.DoanhThuChartActivity;
import com.example.quanlyquanan.ThongKeActivity;
import com.example.quanlyquanan.ThongKeMonAnChartActivity;
import com.example.quanlyquanan.Adapter.AdapterThongKe;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.R;
import com.example.quanlyquanan.TrangChuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ThongKeFragment extends Fragment {

    ListView lvthongkeFragment;
    List<DonDat> donDatS;
    DonDatDAO donDatDAO;
    FloatingActionButton floatingActionButton;
    AdapterThongKe adapterThongKe;
    FragmentManager fragmentManager;
    int madon, manv, maban;
    String ngaydat, tongtien;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvthongkeFragment = view.findViewById(R.id.lvthongkeFragment);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        donDatDAO = new DonDatDAO(getActivity());
        nhanVienDAO = new NhanVienDAO(getActivity());
        banAnDAO = new BanAnDAO(getActivity());
        donDatS = donDatDAO.LayDSDonDat();
        adapterThongKe = new AdapterThongKe(getActivity(), R.layout.thongke, donDatS);
        lvthongkeFragment.setAdapter(adapterThongKe);
        adapterThongKe.notifyDataSetChanged();

        lvthongkeFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon = donDatS.get(position).getMaDonDat();
                manv = donDatS.get(position).getMaNV();
                maban = donDatS.get(position).getMaBan();
                ngaydat = donDatS.get(position).getNgayDat();
                tongtien = donDatS.get(position).getTongTien();

                Intent intent = new Intent(getActivity(), ThongKeActivity.class);
                intent.putExtra("madon", madon);
                intent.putExtra("manv", manv);
                intent.putExtra("maban", maban);
                intent.putExtra("ngaydat", ngaydat);
                intent.putExtra("tongtien", tongtien);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            try {
                exportAllOrdersToWord();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Lỗi xuất Word: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.thongke, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.tkdoanhthu:
                getTkThang();
                return true;
            case R.id.tkmathang:
                getTkMonAn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getTkThang() {
        Intent intent = new Intent(getActivity(), DoanhThuChartActivity.class);
        startActivity(intent);
    }

    private void getTkMonAn() {
        Intent intent = new Intent(getActivity(), ThongKeMonAnChartActivity.class);
        startActivity(intent);
    }

    private void exportAllOrdersToWord() throws IOException {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Danh sách hóa đơn");
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        XWPFTable table = document.createTable();
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Mã Đơn");
        headerRow.addNewTableCell().setText("Ngày Đặt");
        headerRow.addNewTableCell().setText("Tên Bàn");
        headerRow.addNewTableCell().setText("Tên NV");
        headerRow.addNewTableCell().setText("Tổng Tiền");

        for (DonDat donDat : donDatS) {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(donDat.getMaDonDat()));
            dataRow.getCell(1).setText(donDat.getNgayDat());

            String tenBan = banAnDAO.LayTenBanTheoMa(donDat.getMaBan());
            String tenNV = nhanVienDAO.LayNVTheoMa(donDat.getMaNV()).getHOTENNV();

            dataRow.getCell(2).setText(tenBan != null ? tenBan : "N/A");
            dataRow.getCell(3).setText(tenNV != null ? tenNV : "N/A");
            dataRow.getCell(4).setText(donDat.getTongTien());
        }

        File path = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "AllOrders.docx");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            document.write(outputStream);
        }

        Toast.makeText(getActivity(), "Xuất file Word thành công!", Toast.LENGTH_LONG).show();
    }

}
