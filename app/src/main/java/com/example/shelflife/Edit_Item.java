package com.example.shelflife;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_Item extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Item item;
    Item old_item;
    EditText input_item_name;
    EditText input_item_quantity;
    TextView date_output;
    LocalDate date;
    String Name;
    double quantity;

    public Edit_Item() {
        // Required empty public constructor
    }



    public Edit_Item newInstance(Item given_item) {
        Edit_Item fragment = new Edit_Item();
        Bundle args = new Bundle();
        args.putSerializable("ARG_PARAM_userinfo", given_item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("ARG_PARAM_userinfo");
            old_item=item;
            assert item != null;
            date = item.getexpiration_date();
            Name= item.getName();
            quantity= item.getquantity();
        }
        }


public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_edit__item, container, false);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date_output =view.findViewById(R.id.Date_detail_output);
        input_item_name=view.findViewById(R.id.Name_edit_input);
        input_item_quantity=view.findViewById(R.id.Quantity_edit_input);
        String temp = "N/A";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            temp = date+"";
        }
        date_output.setText(temp);
        input_item_name.setText(Name);
        temp = ""+quantity;
        input_item_quantity.setText(temp);




        view.findViewById(R.id.Back_detail_button).setOnClickListener(v -> EListener.Back());
        view.findViewById(R.id.Calender_edit_button).setOnClickListener(v -> EListener.to_edit_Calender(date));
        view.findViewById(R.id.Submit_detail_button).setOnClickListener(v -> {
            boolean quantcheck = true;
            try {
                quantity = Double.valueOf(input_item_quantity.getText().toString());
                quantcheck = false;
            } catch (NumberFormatException exception) {
                Toast.makeText(getActivity(), "Please enter valid Quantity", Toast.LENGTH_SHORT).show();

            }
            if (quantcheck) {

            } else if (quantity <= 0) {
                Toast.makeText(getActivity(), "Please enter a Quantity that is greater then 0", Toast.LENGTH_SHORT).show();
            } else if (input_item_name.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter the name of the item", Toast.LENGTH_SHORT).show();
            } else {
                Item new_item = new Item(input_item_name.getText().toString(), old_item.getdate_added(), date, quantity);
                EListener.edit_item(old_item, new_item);
            }

        });
        view.findViewById(R.id.Delete_edit_button).setOnClickListener(v -> EListener.delete_item(old_item));

    }

EditListener EListener;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EListener = (EditListener) context;


    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public interface EditListener{
        void Back();
        void to_edit_Calender(LocalDate new_date);
        void edit_item(Item old_item,Item new_item);
        void delete_item(Item old_item);


    }
}