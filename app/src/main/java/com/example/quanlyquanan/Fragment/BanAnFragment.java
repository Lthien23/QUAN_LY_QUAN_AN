package com.example.quanlyquanan.Fragment;

// Import các thư viện cần thiết
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

import com.example.quanlyquanan.ThemBanAnActivity;
import com.example.quanlyquanan.SuaBanActivity;
import com.example.quanlyquanan.TrangChuActivity;
import com.example.quanlyquanan.Adapter.AdapterBanAn;
import com.example.quanlyquanan.DAO.BanAnDAO;
import com.example.quanlyquanan.Model.BanAn;
import com.example.quanlyquanan.R;

import java.util.List;

public class BanAnFragment extends Fragment {

    // Các thành phần giao diện và dữ liệu cần dùng
    GridView gvbananFragment; // GridView hiển thị danh sách bàn ăn
    List<BanAn> banAnDTOList; // Danh sách các đối tượng bàn ăn
    BanAnDAO banAnDAO; // DAO để thao tác với cơ sở dữ liệu
    AdapterBanAn adapterBanAn; // Adapter để gắn dữ liệu lên GridView

    // Kích hoạt Activity để thêm bàn ăn và xử lý kết quả trả về
    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData(); // Nhận dữ liệu trả về từ Activity
                        boolean ktra = intent.getBooleanExtra("ketquathem", false); // Kiểm tra kết quả thêm
                        if (ktra) {
                            HienThiDSBan(); // Cập nhật danh sách bàn ăn
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    // Kích hoạt Activity để sửa bàn ăn và xử lý kết quả trả về
    ActivityResultLauncher<Intent> resultLauncherEdit = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData(); // Nhận dữ liệu trả về từ Activity
                        boolean ktra = intent.getBooleanExtra("ketquasua", false); // Kiểm tra kết quả sửa
                        if (ktra) {
                            HienThiDSBan(); // Cập nhật danh sách bàn ăn
                            Toast.makeText(getActivity(), getResources().getString(R.string.edit_sucessful), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.edit_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Khởi tạo giao diện Fragment
        View view = inflater.inflate(R.layout.fragment_ban_an, container, false);

        // Cho phép Fragment có menu riêng
        setHasOptionsMenu(true);

        // Cài đặt tiêu đề cho ActionBar
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle("Quản lý bàn");

        // Liên kết GridView với thành phần trong giao diện
        gvbananFragment = view.findViewById(R.id.gvbananFragment);

        // Khởi tạo DAO để thao tác với cơ sở dữ liệu
        banAnDAO = new BanAnDAO(getActivity());

        // Hiển thị danh sách bàn ăn lên giao diện
        HienThiDSBan();

        // Đăng ký context menu cho GridView
        registerForContextMenu(gvbananFragment);

        return view; // Trả về giao diện của Fragment
    }

    // Tạo context menu khi long-click vào GridView
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu); // Inflate menu từ file XML
    }

    // Xử lý sự kiện khi chọn một mục trong context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId(); // Lấy ID của menu item được chọn
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position; // Lấy vị trí của item được chọn trong GridView
        int maban = banAnDTOList.get(vitri).getMaBan(); // Lấy mã bàn tương ứng

        switch (id) {
            case R.id.itEdit: // Sửa bàn ăn
                Intent intent = new Intent(getActivity(), SuaBanActivity.class);
                intent.putExtra("maban", maban); // Truyền mã bàn sang Activity sửa
                resultLauncherEdit.launch(intent); // Kích hoạt Activity sửa bàn
                break;

            case R.id.itDelete: // Xóa bàn ăn
                boolean ktraxoa = banAnDAO.XoaBanTheoMa(maban); // Xóa bàn ăn trong cơ sở dữ liệu
                if (ktraxoa) {
                    HienThiDSBan(); // Cập nhật danh sách bàn ăn
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    // Tạo menu trên ActionBar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddTable = menu.add(1, R.id.itAddTable, 1, R.string.addTable); // Thêm menu item
        itAddTable.setIcon(R.drawable.ic_baseline_add_24); // Đặt icon cho menu item
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // Hiển thị trên ActionBar nếu có đủ chỗ
    }

    // Xử lý sự kiện khi chọn một mục trong menu trên ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Lấy ID của menu item được chọn
        if (id == R.id.itAddTable) { // Trường hợp chọn mục "Thêm bàn"
            Intent iAddTable = new Intent(getActivity(), ThemBanAnActivity.class); // Tạo intent mở Activity thêm bàn
            resultLauncherAdd.launch(iAddTable); // Kích hoạt Activity thêm bàn
        }
        return super.onOptionsItemSelected(item);
    }

    // Xử lý khi Fragment được hiển thị lại
    @Override
    public void onResume() {
        super.onResume();
        adapterBanAn.notifyDataSetChanged(); // Thông báo Adapter cập nhật dữ liệu
    }

    // Hàm hiển thị danh sách bàn ăn lên GridView
    private void HienThiDSBan() {
        banAnDTOList = banAnDAO.LayTatCaBanAn(); // Lấy danh sách bàn ăn từ cơ sở dữ liệu
        adapterBanAn = new AdapterBanAn(getActivity(), R.layout.banan, banAnDTOList); // Tạo Adapter với danh sách dữ liệu
        gvbananFragment.setAdapter(adapterBanAn); // Gắn Adapter cho GridView
        adapterBanAn.notifyDataSetChanged(); // Thông báo Adapter cập nhật dữ liệu
    }
}
