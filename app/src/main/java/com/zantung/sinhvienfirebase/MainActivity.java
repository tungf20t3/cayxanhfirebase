package com.zantung.sinhvienfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zantung.sinhvienfirebase.adapter.SinhVienAdapter;
import com.zantung.sinhvienfirebase.model.CayXanh;
import com.zantung.sinhvienfirebase.model.sinhvien;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtTenKH, edtTenTG, edtDacTinh, edtMauLa, imganhCayXanh;
    private Button btnAddCX;
    private RecyclerView rcvSinhVien;
    private SinhVienAdapter mAdapter;
    private List<sinhvien> mListSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        btnAddCX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =
                String tenKH = edtTenKH.getText().toString().trim();
                String tenTG = edtTenTG.getText().toString().trim();
                String dacTinh = edtDacTinh.getText().toString().trim();
                String mauLa = edtMauLa.getText().toString().trim();
                String hinhAnh = imganhCayXanh.getText().toString().trim();

                CayXanh cayXanh = new CayXanh(id, tenKH, tenTG, dacTinh, mauLa, hinhAnh);
                onClickAddSV(cayXanh);
            }
        });
        getListFromFireBase();
    }

    private void AnhXa() {
        edtTenKH = findViewById(R.id.edt_tenKH);
        edtTenTG = findViewById(R.id.edt_tenTG);
        edtDacTinh = findViewById(R.id.edt_dacTinh);
        edtMauLa = findViewById(R.id.edt_mauLa);
        imganhCayXanh = findViewById(R.id.edt_hinhAnh);
        btnAddCX = findViewById(R.id.btn_cayXanh);
        rcvSinhVien = findViewById(R.id.rcv_SV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSinhVien.setLayoutManager(linearLayoutManager);
        mListSV = new ArrayList<>();
        mAdapter = new SinhVienAdapter(mListSV, new SinhVienAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(sinhvien sv) {
                openDialogUpdateItem(sv);
            }

            @Override
            public void onClickDeleteItem(sinhvien sv) {
                DeleleItem(sv);
            }
        });
        rcvSinhVien.setAdapter(mAdapter);
    }

    private void onClickAddSV(CayXanh cayXanh) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CayXanh");

        String pathObject = String.valueOf(cayXanh.getId());
        myRef.child(pathObject).setValue(cayXanh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Thêm cây xanh thành công", Toast.LENGTH_SHORT).show();
            }
        });
//        edtID.setText("");
//        edtName.setText("");
    }

    private void getListFromFireBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SinhVien");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListSV != null){
//                    mListSV.clear();
//                }
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                    sinhvien svien = dataSnapshot.getValue(sinhvien.class);
//                    mListSV.add(svien);
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Load list sinh vien faild", Toast.LENGTH_SHORT).show();
//            }
//        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                sinhvien svien = snapshot.getValue(sinhvien.class);
                if (svien != null) {
                    mListSV.add(svien);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                sinhvien svien = snapshot.getValue(sinhvien.class);
                if (svien == null || mListSV == null || mListSV.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListSV.size(); i++){
                    if (svien.getId() == mListSV.get(i).getId()){
                        mListSV.set(i, svien);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                sinhvien svien = snapshot.getValue(sinhvien.class);
                if (svien == null || mListSV == null || mListSV.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListSV.size(); i++){
                    if (svien.getId() == mListSV.get(i).getId()){
                        mListSV.remove(mListSV.get(i));
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

    private void openDialogUpdateItem(sinhvien sv){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText  edtupdatename = dialog.findViewById(R.id.edt_NewName);
        Button btnCancel = dialog.findViewById(R.id.btn_dialogCancel);
        Button btnUpdate = dialog.findViewById(R.id.btn_dialogUpdate);
        //hien ten len editText
        edtupdatename.setText(sv.getTen());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("SinhVien");

                String newName = edtupdatename.getText().toString().trim();
                sv.setTen(newName);

                myRef.child(String.valueOf(sv.getId())).updateChildren(sv.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Update sinh viên thành công !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void DeleleItem(sinhvien sv){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("SinhVien");

                        myRef.child(String.valueOf(sv.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }
}