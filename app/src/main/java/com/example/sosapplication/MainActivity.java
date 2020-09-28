package com.example.sosapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sosapplication.DAO.NguoiDung;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;

public class MainActivity extends AppCompatActivity {

    TextView textViewName, textViewSDT, textViewMaND;
    Button btnDangXuat;

    NguoiDung nguoiDung;
    String ma_nd, hoten, sdt;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = findViewById(R.id.textViewName);
        textViewSDT = findViewById(R.id.textViewSDT);
        textViewMaND = findViewById(R.id.textViewMaND);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        firebaseAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
//        nguoiDung = new NguoiDung();
        ma_nd = firebaseAuth.getCurrentUser().getUid();
        myRef.child("NguoiDung").child(ma_nd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nguoiDung = snapshot.getValue(NguoiDung.class);
                textViewName.setText(nguoiDung.getHoTen());
                textViewSDT.setText(nguoiDung.getSdt());
                textViewMaND.setText(nguoiDung.getMa_nd());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


}