package com.example.quanlyquanan.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.quanlyquanan.ThemLoaiMonActivity;
import com.example.quanlyquanan.ThemMonAnActivity;
import com.example.quanlyquanan.SoLuongActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.Adapter.AdapterLoaiMon;
import com.example.quanlyquanan.Adapter.AdapterMonAn;
import com.example.quanlyquanan.DAO.MonDAO;
import com.example.quanlyquanan.Model.Mon;
import com.example.quanlyquanan.R;

import java.util.List;

public class MonAnFragment extends Fragment {

    int maloai, maban;
    String tenloai,tinhtrang;
    GridView gvMonanfragment;
    MonDAO monDAO;
    List<Mon> monList;
    AdapterMonAn adapterMonAn;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themmon"))
                        {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSMon();
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

        View view = inflater.inflate(R.layout.fragment_mon_an,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle("Quản lý món ăn");
        monDAO = new MonDAO(getActivity());

        gvMonanfragment = (GridView)view.findViewById(R.id.gvMonanfragment);

        Bundle bundle = getArguments();
        if(bundle !=null){
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvMonanfragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    tinhtrang = monList.get(position).getTinhTrang();
                    if(maban != 0){
                        if(tinhtrang.equals("true")){
                            Intent iAmount = new Intent(getActivity(), SoLuongActivity.class);
                            iAmount.putExtra("maban",maban);
                            iAmount.putExtra("mamon",monList.get(position).getMaMon());
                            startActivity(iAmount);
                        }else {
                            Toast.makeText(getActivity(),"Món đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvMonanfragment);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

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
        int mamon = monList.get(vitri).getMaMon();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), ThemMonAnActivity.class);
                iEdit.putExtra("mamon",mamon);
                iEdit.putExtra("maLoai",maloai);
                iEdit.putExtra("tenLoai",tenloai);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = monDAO.XoaMon(mamon);
                if(ktra){
                    HienThiDSMon();
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
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), ThemMonAnActivity.class);
                intent.putExtra("maLoai",maloai);
                intent.putExtra("tenLoai",tenloai);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void HienThiDSMon(){
        monList = monDAO.LayDSMonTheoLoai(maloai);
        adapterMonAn = new AdapterMonAn(getActivity(),R.layout.monan,monList);
        gvMonanfragment.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

}