package com.example.shelflife;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Item_Adapter extends ArrayAdapter<Item> {
    public Item_Adapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_row_layout,parent,false);
        }
        Item item = getItem(position);
        TextView Item_name = convertView.findViewById(R.id.Item_output);
        TextView date_added = convertView.findViewById(R.id.Date_Added);
        TextView expiration_date = convertView.findViewById(R.id.Expiration_date);
        String temp = "";

        assert item != null;
        Item_name.setText(item.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            temp = item.getdate_added().getMonthValue() +" " +item.getdate_added().getDayOfMonth()+" "+item.getdate_added().getYear();

        }
        date_added.setText(temp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            temp = item.getexpiration_date().getMonthValue()+" " +item.getexpiration_date().getDayOfMonth()+" "+item.getexpiration_date().getYear();
        }
        expiration_date.setText(temp);



        return convertView;
    }
}
