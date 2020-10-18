package com.example.sosapplication.ui.contacts;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sosapplication.DAO.LienHe;
import com.example.sosapplication.DAO.NguoiDung;
import com.example.sosapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContactsFragment extends Fragment {

    private ContactsViewModel mViewModel;
    FloatingActionButton themLienHe;
    ListView listContacts;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef, myRef2;

    String ma_nd;
    HashMap<NguoiDung, LienHe> quanhe;
    ArrayList<Map.Entry<NguoiDung, LienHe>> listQuanHe;
    CustomListAdapter adapter;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contacts_fragment, container, false);

        listContacts = root.findViewById(R.id.listContacts);
        firebaseAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
        myRef2 = database.getReference();
        ma_nd = firebaseAuth.getCurrentUser().getUid();

        quanhe = new HashMap<NguoiDung,LienHe>();
//        NguoiDung nd1 = new NguoiDung("asd","123","asd");
//        LienHe lh1 = new LienHe("asdasd","nguoithan",1);
//        NguoiDung nd2 = new NguoiDung("asdddd","1233333","asddddd");
//        LienHe lh2 = new LienHe("asdasddddd","nguoithannn",2);
//        quanhe.put(nd1,lh1);
//        quanhe.put(nd2,lh2);
        Set<Map.Entry<NguoiDung, LienHe>> entrySet = quanhe.entrySet();

        listQuanHe = new ArrayList<>();
        listQuanHe.addAll(entrySet);
        adapter = new CustomListAdapter(getActivity(),listQuanHe);
        listContacts.setAdapter(adapter);

        myRef.child("LienHe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<LienHe> ndLienHe = layLH(snapshot,ma_nd);
                    for (final LienHe maNdLienhe : ndLienHe){
                        final String maNdLh = layMaNdLh(maNdLienhe.getMa_lh(),ma_nd);
                        myRef2.child("NguoiDung").child(maNdLh).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                if (snapshot1.exists()){
                                    NguoiDung nd = snapshot1.getValue(NguoiDung.class);
                                    quanhe = new HashMap<NguoiDung,LienHe>();
                                    quanhe.put(nd,maNdLienhe);
                                    Set<Map.Entry<NguoiDung, LienHe>> entrySet = quanhe.entrySet();;
                                    listQuanHe.addAll(entrySet);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        themLienHe = root.findViewById(R.id.themLienHe);
        themLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ThemLHActivity.class);
                startActivity(intent);
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        // TODO: Use the ViewModel
    }

    private List<LienHe> layLH(DataSnapshot dsn, String ma1) {
        List<LienHe> maLh = new ArrayList<>();
        for (DataSnapshot ds : dsn.getChildren()) {
            if ((ds.getKey().startsWith(ma1)) || (ds.getKey().endsWith(ma1))) {
                maLh.add(ds.getValue(LienHe.class));
            }
        }
        return maLh;
    }
    private String layMaNdLh(String malh, String ma1) {
        String ma2 = new String();
        ma2 = malh.replace(ma1,"");
        return ma2;

    }

}