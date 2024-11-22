package com.example.quanlyquanan.Adapter;

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

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.ThanhToanActivity;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.example.quanlyquanan.Model.BanAn;
import com.example.quanlyquanan.Model.DonDat;
import com.example.quanlyquanan.Fragment.LoaiMonFragment;
import com.example.quanlyquanan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterBanAn extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAn> banAnList;
    ViewHolder viewHolder;
    BanAnDAO banAnDAO;
    DonDatDAO donDatDAO;
    FragmentManager fragmentManager;

    public AdapterBanAn(Context context, int layout, List<BanAn> banAnList) {
        this.context = context;
        this.layout = layout;
        this.banAnList = banAnList;
        banAnDAO = new BanAnDAO(context);
        donDatDAO = new DonDatDAO(context);
        fragmentManager = ((TrangChuActivity) context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout, parent, false);
            viewHolder.imgBanAn = view.findViewById(R.id.img_banan_BanAn);
            viewHolder.imgGoiMon = view.findViewById(R.id.img_banan_GoiMon);
            viewHolder.imgThanhToan = view.findViewById(R.id.img_banan_ThanhToan);
            viewHolder.txtTenBanAn = view.findViewById(R.id.txt_banan_TenBanAn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BanAn banAn = banAnList.get(position);
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAn.getMaBan());

        if (kttinhtrang.equals("true")) {
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_person_24);
        } else {
            viewHolder.imgBanAn.setImageResource(R.drawable.img);
        }

        viewHolder.txtTenBanAn.setText(banAn.getTenBan());
        viewHolder.imgBanAn.setTag(position);

        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();
        int vitri1 = (int) viewHolder.imgBanAn.getTag();
        int maban = banAnList.get(vitri1).getMaBan();
        String tenban = banAnList.get(vitri1).getTenBan();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat = dateFormat.format(calendar.getTime());

        switch (id) {
            case R.id.img_banan_BanAn:
                int vitri = (int) v.getTag();
                banAnList.get(vitri).setDuocChon(true);
                break;
            case R.id.img_banan_GoiMon:
                Intent getIHome = ((TrangChuActivity) context).getIntent();
                int manv = getIHome.getIntExtra("manv", 0);
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if (tinhtrang.equals("false")) {
                    DonDat donDat = new DonDat();
                    donDat.setMaBan(maban);
                    donDat.setMaNV(manv);
                    donDat.setNgayDat(ngaydat);
                    donDat.setTinhTrang("false");
                    donDat.setTongTien("0");

                    long ktra = donDatDAO.ThemDonDat(donDat);
                    banAnDAO.CapNhatTinhTrangBan(maban, "true");

                    if (ktra == 0) {
                        Toast.makeText(context, context.getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                LoaiMonFragment displayCategoryFragment = new LoaiMonFragment();
                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban", maban);
                displayCategoryFragment.setArguments(bDataCategory);
                transaction.replace(R.id.contentView, displayCategoryFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;

            case R.id.img_banan_ThanhToan:
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban", maban);
                iThanhToan.putExtra("tenban", tenban);
                ((Activity) context).startActivityForResult(iThanhToan, 55);
                break;
        }
    }

    public class ViewHolder {
        ImageView imgBanAn, imgGoiMon, imgThanhToan;
        TextView txtTenBanAn;
    }
}
