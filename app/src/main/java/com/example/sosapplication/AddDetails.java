package com.example.sosapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sosapplication.DAO.NguoiDung;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDetails extends AppCompatActivity {
    public static final String TAG = "TAG";

    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    EditText editTextName;
    Button btnSave;
    NguoiDung nguoiDung;
    String ma_nd;
    String sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        editTextName = findViewById(R.id.editTextTextPersonName);
        btnSave = findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        ma_nd = firebaseAuth.getCurrentUser().getUid();
        sdt = firebaseAuth.getCurrentUser().getPhoneNumber();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextName.getText().toString().isEmpty()){

                    nguoiDung = new NguoiDung(ma_nd, sdt ,editTextName.getText().toString());
                    myRef.child("NguoiDung").child(ma_nd).setValue(nguoiDung, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null){
                                Toast.makeText(AddDetails.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                                Intent myIt = new Intent(AddDetails.this, MainActivity2.class);
                                AddDetails.this.startActivity(myIt);
                            } else {
                                Toast.makeText(AddDetails.this, "That Bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}