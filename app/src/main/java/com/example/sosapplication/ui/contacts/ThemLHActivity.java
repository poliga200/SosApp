package com.example.sosapplication.ui.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sosapplication.DAO.LienHe;
import com.example.sosapplication.MainActivity2;
import com.example.sosapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ThemLHActivity extends AppCompatActivity {

    EditText editTextPhoneLH, editTextUuTien;
    RadioButton radioButton, radioButton2;
    Button buttonThemLH, buttonHuyThemLH;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef, myRef2;

    String ma_nd, sdt_lh, loai, ma_lh, ma_nd2;
    LienHe lienHe;
    int dut = 0;
    int kiemTraSDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_l_h);

        editTextPhoneLH = findViewById(R.id.editTextPhoneLH);
        editTextUuTien = findViewById(R.id.editTextUuTien);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        buttonThemLH = findViewById(R.id.buttonThemLH);
        buttonHuyThemLH = findViewById(R.id.buttonHuyThemLH);

        firebaseAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
        myRef2 = database.getReference();
        ma_nd = firebaseAuth.getCurrentUser().getUid();

        buttonHuyThemLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonThemLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextPhoneLH.getText().toString().isEmpty()) {
                    sdt_lh = editTextPhoneLH.getText().toString();
                } else {
                    editTextPhoneLH.setError("Nhap SDT");
                }
                if (radioButton.isChecked()) {
                    loai = radioButton.getText().toString();
                } else {
                    if (radioButton2.isChecked()) {
                        loai = radioButton2.getText().toString();
                    }
                }
                if (!editTextUuTien.getText().toString().isEmpty()) {
                    dut = Integer.parseInt(String.valueOf(editTextUuTien.getText()));
                } else {
                    editTextUuTien.setError("Nhap Do Uu Tien");
                }
                if (!sdt_lh.isEmpty() && !editTextUuTien.getText().toString().isEmpty()) {
                    kiemTraSDT = 0;
                    myRef.child("NguoiDung").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ns : snapshot.getChildren()) {
                                if (ns.child("sdt").getValue().equals(sdt_lh)) {
                                    kiemTraSDT = 1;
                                    ma_nd2 = ns.getKey();
                                    if (!ma_nd.equals(ma_nd2)) {
                                        myRef.child("LienHe").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                for (DataSnapshot checkns : snapshot1.getChildren()) {
                                                    if (checkLH(checkns, ma_nd, ma_nd2)) {
                                                        kiemTraSDT = 2;
                                                        break;
                                                    }
                                                }
                                                if (kiemTraSDT == 1) {
                                                    ma_lh = ma_nd.concat(ma_nd2);
                                                    lienHe = new LienHe(ma_lh, loai, dut);
//                                                    HashMap<String, String> lh = new HashMap<>();
//                                                    lh.put("loai", lienHe.getLoai());
//                                                    lh.put("dut", String.valueOf(lienHe.getDut()));
                                                    myRef.child("LienHe").child(ma_lh).setValue(lienHe);
                                                    Toast.makeText(ThemLHActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                } else {
                                                    if (kiemTraSDT == 2) {
                                                        editTextPhoneLH.setError("Da co lien he!");

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });

                                    } else {
                                        kiemTraSDT = 3;
                                        editTextPhoneLH.setError("SDT nay la cua ban");
                                        break;
                                    }
                                }
                            }
                            if (kiemTraSDT == 0) {
                                editTextPhoneLH.setError("SDT chua dang ky nguoi dung");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    private Boolean checkLH(DataSnapshot dsn, String ma1, String ma2) {
        Boolean c = false;
        if ((dsn.getKey().startsWith(ma1) && dsn.getKey().endsWith(ma2)) || (dsn.getKey().startsWith(ma2) && dsn.getKey().endsWith(ma1))) {
            c = true;
        }
        return c;
    }
}