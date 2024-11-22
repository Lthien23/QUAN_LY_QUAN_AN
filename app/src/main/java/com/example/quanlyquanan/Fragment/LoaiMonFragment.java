package com.example.quanlyquanan.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlyquanan.ThemLoaiMonActivity;
import com.example.quanlyquanan.ThemMonAnActivity;
import com.example.quanlyquanan.ThemNhanVienActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.Adapter.AdapterLoaiMon;
import com.example.quanlyquanan.DAO.LoaiMonDAO;
import com.example.quanlyquanan.Model.LoaiMon;
import com.example.quanlyquanan.R;

import java.util.List;

public class LoaiMonFragment extends Fragment {

    GridView gvLoaimonFragment;
    List<LoaiMon> loaiMonList;
    LoaiMonDAO loaiMonDAO;
    AdapterLoaiMon adapter;
    FragmentManager fragmentManager;
    int maban;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Sủa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loai_mon,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle("Quản lý loại món");

        gvLoaimonFragment = (GridView)view.findViewById(R.id.gvLoaimonFragment);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonDAO = new LoaiMonDAO(getActivity());
        HienThiDSLoai();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            maban = bDataCategory.getInt("maban");
        }

        gvLoaimonFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonList.get(position).getMaLoai();
                String tenloai = loaiMonList.get(position).getTenLoai();
                MonAnFragment monAnFragment = new MonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("tenloai",tenloai);
                bundle.putInt("maban",maban);
                monAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,monAnFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });

        registerForContextMenu(gvLoaimonFragment);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonList.get(vitri).getMaLoai();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), ThemLoaiMonActivity.class);
                iEdit.putExtra("maloai",maloai);
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = loaiMonDAO.XoaLoaiMon(maloai);
                if(ktra){
                    HienThiDSLoai();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                Intent intent = new Intent(getActivity(), ThemLoaiMonActivity.class);
                resultLauncherCategory.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSLoai(){
        loaiMonList = loaiMonDAO.LayDSLoaiMon();
        adapter = new AdapterLoaiMon(getActivity(),R.layout.loaimon,loaiMonList);
        gvLoaimonFragment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}