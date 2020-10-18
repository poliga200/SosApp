package com.example.sosapplication.ui.contacts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sosapplication.DAO.LienHe;
import com.example.sosapplication.DAO.NguoiDung;
import com.example.sosapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListAdapter extends BaseAdapter {

    List<Map.Entry<NguoiDung,LienHe>> listQuanHe;
    Context context;
    NguoiDung[] mKeys;
    LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, List<Map.Entry<NguoiDung,LienHe>> listQuanHe) {
        this.listQuanHe = listQuanHe;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listQuanHe.size();
    }

    @Override
    public Object getItem(int position) {
        return listQuanHe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_contacts_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textListName = convertView.findViewById(R.id.textListName);
            viewHolder.textListDUT = convertView.findViewById(R.id.textListDUT);
            viewHolder.textListSDT = convertView.findViewById(R.id.textListSDT);
            viewHolder.imageBtCall = convertView.findViewById(R.id.imageBtCall);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map.Entry<NguoiDung,LienHe> quanhe = listQuanHe.get(position);
        String ten = quanhe.getKey().getHoTen();
        String dut = String.valueOf(quanhe.getValue().getDut());
        String sdt = quanhe.getKey().getSdt();
        viewHolder.textListName.setText(ten);
        viewHolder.textListDUT.setText(dut);
        viewHolder.textListSDT.setText(sdt);

        return convertView;
    }

    static class ViewHolder{
        TextView textListName, textListDUT, textListSDT;
        ImageButton imageBtCall;
    }
}
