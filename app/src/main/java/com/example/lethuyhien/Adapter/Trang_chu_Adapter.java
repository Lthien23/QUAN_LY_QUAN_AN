package com.example.lethuyhien.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lethuyhien.Model.Trang_chu;
import com.example.lethuyhien.R;

import java.util.List;

public class Trang_chu_Adapter extends RecyclerView.Adapter<Trang_chu_Adapter.TrangChuViewHolder> {

    private Context context;
    private List<Trang_chu> trangChuList;

    // Constructor
    public Trang_chu_Adapter(Context context, List<Trang_chu> trangChuList) {
        this.context = context;
        this.trangChuList = trangChuList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TrangChuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_banan, parent, false);  // Inflate layout for each item
        return new TrangChuViewHolder(view);
    }

    // Bind data to the views (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull TrangChuViewHolder holder, int position) {
        // Get the current item from the list
        Trang_chu trangChu = trangChuList.get(position);

        // Set the values for the TextViews
        holder.txtTenMon.setText(String.valueOf(trangChu.getId_ban()));
        holder.txtSoCho.setText(trangChu.getSocho());
        holder.txtTrangThai.setText(trangChu.getTrangthai());

        // Set the image for ImageView
        if (trangChu.getAnh() != null) {
            byte[] image = trangChu.getAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imgHinhMon.setImageBitmap(bitmap);
        } else {
            holder.imgHinhMon.setImageResource(R.drawable.ban_an);  // Default image resource
        }
    }

    // Return the size of the list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return trangChuList.size();
    }

    // ViewHolder class to store item views
    public static class TrangChuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhMon;
        TextView txtTenMon, txtSoCho, txtTrangThai;

        public TrangChuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhMon = itemView.findViewById(R.id.anhbanan);
            txtTenMon = itemView.findViewById(R.id.idban);
            txtSoCho = itemView.findViewById(R.id.idsocho);
            txtTrangThai = itemView.findViewById(R.id.idtrangthai);
            // Gán sự kiện LongClick cho item
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(position);  // Gọi phương thức long click
                    }
                }
                return true;
            });
        }
    }
}
