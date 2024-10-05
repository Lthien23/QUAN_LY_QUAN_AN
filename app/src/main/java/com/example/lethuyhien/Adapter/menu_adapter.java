package com.example.lethuyhien.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lethuyhien.Model.Menu;
import com.example.lethuyhien.R;

import java.util.ArrayList;

public class menu_adapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Menu> list;

    public menu_adapter(Context context, ArrayList<Menu> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(R.layout.item_menu, viewGroup, false);

        ImageView imageView = view.findViewById(R.id.anhmonan);
        TextView ten = view.findViewById(R.id.idten);
        TextView gia1 = view.findViewById(R.id.idgiamenu);
        TextView loai = view.findViewById(R.id.idloaimenu);

        // Tham chiếu đến các nút và TextView số lượng
        Button btnTang = view.findViewById(R.id.cong);
        Button btnGiam = view.findViewById(R.id.tru);
        final TextView txtSoluong = view.findViewById(R.id.slmonanmenu);

        // Thiết lập dữ liệu cho món ăn
        imageView.setImageResource(list.get(i).getAnhmenu());
        ten.setText(String.valueOf(list.get(i).getTenmonan()));
        gia1.setText(String.valueOf(list.get(i).getGia()));
        loai.setText(list.get(i).getLoai());

        // Thiết lập số lượng mặc định
        final int[] Soluong = {list.get(i).getSoluong()};
        txtSoluong.setText(String.valueOf(Soluong[0]));

        // Xử lý sự kiện nhấn nút "+"
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Soluong[0]++;
                txtSoluong.setText(String.valueOf(Soluong[0]));
                list.get(i).setSoluong(Soluong[0]); // Cập nhật số lượng vào danh sách
            }
        });

        // Xử lý sự kiện nhấn nút "-"
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Soluong[0] > 0) {
                    Soluong[0]--;
                    txtSoluong.setText(String.valueOf(Soluong[0]));
                    list.get(i).setSoluong(Soluong[0]); // Cập nhật số lượng vào danh sách
                }
            }
        });

        return view;
    }
}
