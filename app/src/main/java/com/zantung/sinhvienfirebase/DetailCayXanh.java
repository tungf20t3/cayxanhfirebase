package com.zantung.sinhvienfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.zantung.sinhvienfirebase.model.CayXanh;

public class DetailCayXanh extends AppCompatActivity {
    private TextView ten_khoa_hoc, ten_thuong_goi, dac_tinh, mau_la;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cay_xanh);
        AnhXa();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        CayXanh cayXanh = (CayXanh) bundle.get("object");
        ten_khoa_hoc.setText(cayXanh.getTen_khoa_hoc());
        ten_thuong_goi.setText(cayXanh.getTen_thuong_goi());
        dac_tinh.setText(cayXanh.getDac_tinh());
        mau_la.setText(cayXanh.getMau_la());

    }

    private void AnhXa(){
        ten_khoa_hoc = findViewById(R.id.tvTenKhoaHocCT);
        ten_thuong_goi = findViewById(R.id.tvtenThuongGoiCT);
        dac_tinh = findViewById(R.id.tvDacTinhCT);
        mau_la = findViewById(R.id.tvMauLaCT);
    }
}