package com.example.quanlyquanan.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyquanan.ThemNhanVienActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.DangKy1Activity;
import com.example.quanlyquanan.Adapter.AdapterNhanVien;
import com.example.quanlyquanan.DAO.NhanVienDAO;
import com.example.quanlyquanan.Model.NhanVien;
import com.example.quanlyquanan.R;

import java.util.List;

public class NhanVienFragment extends Fragment {

    GridView gvnhanvienFragment;
    NhanVienDAO nhanVienDAO;
    List<NhanVien> nhanVienS;
    AdapterNhanVien adapterNhanVien;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long ktra = intent.getLongExtra("ketquaktra",0);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themnv"))
                        {
                            if(ktra != 0){
                                HienThiDSNV();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra != 0){
                                HienThiDSNV();
                                Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhan_vien,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle("Quản lý nhân viên");
        setHasOptionsMenu(true);

        gvnhanvienFragment = (GridView)view.findViewById(R.id.gvnhanvienFragment) ;

        nhanVienDAO = new NhanVienDAO(getActivity());
        HienThiDSNV();

        registerForContextMenu(gvnhanvienFragment);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manv = nhanVienS.get(vitri).getMANV();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(),ThemNhanVienActivity.class);
                iEdit.putExtra("manv",manv);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = nhanVienDAO.XoaNV(manv);
                if(ktra){
                    HienThiDSNV();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1,R.id.itAddStaff,1,"Thêm nhân viên");
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddStaff:
                Intent iDangky = new Intent(getActivity(), ThemNhanVienActivity.class);
                resultLauncherAdd.launch(iDangky);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSNV(){
        nhanVienS = nhanVienDAO.LayDSNV();
        adapterNhanVien = new AdapterNhanVien(getActivity(),R.layout.nhanvien,nhanVienS);
        gvnhanvienFragment.setAdapter(adapterNhanVien);
        adapterNhanVien.notifyDataSetChanged();
    }
}