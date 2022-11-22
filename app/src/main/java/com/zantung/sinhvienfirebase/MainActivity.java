package com.zantung.sinhvienfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zantung.sinhvienfirebase.adapter.CayXanhAdapter;
import com.zantung.sinhvienfirebase.model.CayXanh;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtTenKH, edtTenTG, edtDacTinh, edtMauLa, imganhCayXanh, edtID;
    private Button btnAddCX;
    private RecyclerView rcvCayXanh;
    private CayXanhAdapter mAdapter;
    private List<CayXanh> mListCayXanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        btnAddCX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(edtID.getText().toString().trim());
                String tenKH = edtTenKH.getText().toString().trim();
                String tenTG = edtTenTG.getText().toString().trim();
                String dacTinh = edtDacTinh.getText().toString().trim();
                String mauLa = edtMauLa.getText().toString().trim();
                String hinhAnh = imganhCayXanh.getText().toString().trim();

                CayXanh cayXanh = new CayXanh(id, tenKH, tenTG, dacTinh, mauLa, hinhAnh);
                onClickAddCayXanh(cayXanh);
            }
        });
        getListFromFireBase();
    }

    private void AnhXa() {
        edtID = findViewById(R.id.edt_ID);
        edtTenKH = findViewById(R.id.edt_tenKH);
        edtTenTG = findViewById(R.id.edt_tenTG);
        edtDacTinh = findViewById(R.id.edt_dacTinh);
        edtMauLa = findViewById(R.id.edt_mauLa);
        imganhCayXanh = findViewById(R.id.edt_hinhAnh);
        btnAddCX = findViewById(R.id.btn_cayXanh);
        rcvCayXanh = findViewById(R.id.rcv_SV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCayXanh.setLayoutManager(linearLayoutManager);

        mListCayXanh = new ArrayList<>();
        mAdapter = new CayXanhAdapter(mListCayXanh, this, new CayXanhAdapter.IClickListener() {
            @Override
            public void onClickDeleteItem(CayXanh cx) {
                DeleleItem(cx);
            }

        });

        rcvCayXanh.setAdapter(mAdapter);
    }

    private void onClickAddCayXanh(CayXanh cayXanh) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CayXanh");

        String pathObject = String.valueOf(cayXanh.getId());
        myRef.child(pathObject).setValue(cayXanh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Thêm cây xanh thành công", Toast.LENGTH_SHORT).show();
            }
        });
        edtID.setText("");
        edtTenKH.setText("");
        edtTenTG.setText("");
        edtDacTinh.setText("");
        edtMauLa.setText("");
        imganhCayXanh.setText("");

    }

    private void getListFromFireBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CayXanh");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CayXanh cayXanh = snapshot.getValue(CayXanh.class);
                if (cayXanh != null) {
                    mListCayXanh.add(cayXanh);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CayXanh cayXanh = snapshot.getValue(CayXanh.class);
                if (cayXanh == null || mListCayXanh == null || mListCayXanh.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListCayXanh.size(); i++){
                    if (cayXanh.getId() == mListCayXanh.get(i).getId()){
                        mListCayXanh.set(i, cayXanh);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                CayXanh cayXanh = snapshot.getValue(CayXanh.class);
                if (cayXanh == null || mListCayXanh == null || mListCayXanh.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListCayXanh.size(); i++){
                    if (cayXanh.getId() == mListCayXanh.get(i).getId()){
                        mListCayXanh.remove(mListCayXanh.get(i));
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DeleleItem(CayXanh cayXanh){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa cây này không ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("CayXanh");

                        myRef.child(String.valueOf(cayXanh.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Xóa cây xanh thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}