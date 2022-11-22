package com.zantung.sinhvienfirebase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zantung.sinhvienfirebase.R;
import com.zantung.sinhvienfirebase.model.sinhvien;

import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.SVViewHolder>{

    private List<sinhvien> ListSV;
    private IClickListener iClickListener;

    public interface IClickListener {
        void onClickUpdateItem(sinhvien sv);
        void onClickDeleteItem(sinhvien sv);
    }

    public SinhVienAdapter(List<sinhvien> listSV, IClickListener listener) {
        this.ListSV = listSV;
        this.iClickListener = listener;
    }

    @NonNull
    @Override
    public SVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cay_xanh, parent, false);
        return new SVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SVViewHolder holder, int position) {
        sinhvien sv = ListSV.get(position);
        if (sv == null){
            return;
        }
        holder.id_sv.setText("ID: " + sv.getId());
        holder.name_sv.setText("Name: " + sv.getTen());
        //bat su kien ra main
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickUpdateItem(sv);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickDeleteItem(sv);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ListSV != null){
            return ListSV.size();
        }
        return 0;
    }

    public class SVViewHolder extends RecyclerView.ViewHolder {

        private TextView id_sv, name_sv;
        private Button btn_update, btn_delete;

        public SVViewHolder(@NonNull View itemView) {
            super(itemView);
            id_sv = itemView.findViewById(R.id.tv_ID);
            name_sv = itemView.findViewById(R.id.tv_Name);
            btn_update = itemView.findViewById(R.id.btn_Update);
            btn_delete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}
