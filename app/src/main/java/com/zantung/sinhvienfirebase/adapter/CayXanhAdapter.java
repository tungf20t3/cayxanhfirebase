package com.zantung.sinhvienfirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zantung.sinhvienfirebase.DetailCayXanh;
import com.zantung.sinhvienfirebase.R;
import com.zantung.sinhvienfirebase.model.CayXanh;

import java.util.List;

public class CayXanhAdapter extends RecyclerView.Adapter<CayXanhAdapter.CayXanhViewHolder> {

    private List<CayXanh> ListCayXanh;
    private Context context;
    private IClickListener iClickListener;

    public interface IClickListener {
        void onClickDeleteItem(CayXanh cx);
    }

    public CayXanhAdapter(List<CayXanh> listCayXanh, Context context, IClickListener iClickListener) {
        ListCayXanh = listCayXanh;
        this.context = context;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public CayXanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cay_xanh, parent, false);
        return new CayXanhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CayXanhAdapter.CayXanhViewHolder holder, int position) {
        final CayXanh cayXanh = ListCayXanh.get(position);
        if (cayXanh == null){
            return;
        }
        holder.ten_khoa_hoc.setText("Tên khoa học: " + cayXanh.getTen_khoa_hoc());
        holder.ten_thuong_goi.setText("Tên thường gọi: " + cayXanh.getTen_thuong_goi());
        holder.dac_tinh.setText("Đặc tính: " + cayXanh.getDac_tinh());
        holder.mau_la.setText("Màu lá: " + cayXanh.getMau_la());
        Glide.with(context).load(cayXanh.getHinh_anh()).into(holder.imgCayXanh);
        //bat su kien ra main
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickDeleteItem(cayXanh);
            }
        });

    }



    @Override
    public int getItemCount() {
        if (ListCayXanh != null){
            return ListCayXanh.size();
        }
        return 0;
    }

    public class CayXanhViewHolder extends RecyclerView.ViewHolder {

        private TextView ten_thuong_goi, ten_khoa_hoc, mau_la, dac_tinh;
        private Button  btn_delete;
        private ImageView imgCayXanh;

        public CayXanhViewHolder(@NonNull View itemView) {
            super(itemView);
            ten_khoa_hoc = itemView.findViewById(R.id.tvTenKhoaHoc);
            ten_thuong_goi = itemView.findViewById(R.id.tvtenThuongGoi);
            dac_tinh = itemView.findViewById(R.id.tvDacTinh);
            mau_la = itemView.findViewById(R.id.tvMauLa);
            imgCayXanh = itemView.findViewById(R.id.imgCay);
            btn_delete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}
