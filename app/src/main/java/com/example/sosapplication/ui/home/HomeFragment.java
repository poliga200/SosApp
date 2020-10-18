package com.example.sosapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sosapplication.DAO.LienHe;
import com.example.sosapplication.DAO.NguoiDung;
import com.example.sosapplication.MainActivity2;
import com.example.sosapplication.R;
import com.example.sosapplication.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView textViewName, textViewSDT, textViewMaND;
    Button btnDangXuat;

    NguoiDung nguoiDung,nguoiDung2;
    String ma_nd, hoten, sdt;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.textViewMaND);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        textViewName = root.findViewById(R.id.textViewName);
        textViewSDT = root.findViewById(R.id.textViewSDT);
        textViewMaND = root.findViewById(R.id.textViewMaND);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);

        firebaseAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
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
                Intent intent = new Intent(getActivity(), Register.class);
                HomeFragment.this.startActivity(intent);
            }
        });

        return root;
    }
}