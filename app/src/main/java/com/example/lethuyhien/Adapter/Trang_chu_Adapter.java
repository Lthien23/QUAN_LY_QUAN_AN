package com.example.lethuyhien.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lethuyhien.Model.Trang_chu;
import com.example.lethuyhien.R;

import java.util.ArrayList;

public class Trang_chu_Adapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Trang_chu> list;

    public Trang_chu_Adapter(Context context, ArrayList<Trang_chu> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return  list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        view=inflater.inflate(R.layout.item_banan,viewGroup,false);
        ImageView imageView=view.findViewById(R.id.anhbanan);
        TextView idban =view.findViewById(R.id.idban);
        TextView socho=view.findViewById(R.id.idsocho);
        TextView trangthai =view.findViewById(R.id.idtrangthai);
        imageView.setImageResource(list.get(i).getAnh());
        idban.setText(String.valueOf(list.get(i).getId_ban()));
        socho.setText(list.get(i).getSocho());
        trangthai.setText(list.get(i).getTrangthai());
        return view;
    }
}
