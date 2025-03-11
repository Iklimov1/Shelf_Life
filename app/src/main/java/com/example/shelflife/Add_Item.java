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
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Item extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LocalDate expiration_date = null;
    TextView date_output;
    boolean check = false;
    EditText input_item_name;
    EditText input_item_quantity;

    public Add_Item() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Item.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Item newInstance(String param1, String param2) {
        Add_Item fragment = new Add_Item();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__item, container, false);
    }
    public void setDate(LocalDate datetemp){
        expiration_date = datetemp;
        check = true;
    }




    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date_output =view.findViewById(R.id.Date_Output);
        input_item_name=view.findViewById(R.id.Name_Input);
        input_item_quantity=view.findViewById(R.id.Quantity_Input);
        if(check){
            String temp = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                temp = "Date: "+expiration_date.getYear()+" "+expiration_date.getMonth()+" "+expiration_date.getDayOfMonth();
            }
            date_output.setText(temp);


        }
        else{
            String temp ="Date: N/A";
            date_output.setText(temp);


        }



        view.findViewById(R.id.Back_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BListener.Back();

            }

        });

        view.findViewById(R.id.Calender_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EListener.GoToCalender();

            }

        });

        view.findViewById(R.id.Save_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate date_added = null;
                String item_name = input_item_name.getText().toString();
                String quantity_string = input_item_quantity.getText().toString();
                double quantity = 0;
                boolean quantcheck= true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date_added = LocalDate.now();
                }
                try {
                    quantity = Double.valueOf(quantity_string);
                    quantcheck = false;
                } catch(NumberFormatException exception) {
                    Toast.makeText(getActivity(), "Please enter valid Quantity", Toast.LENGTH_SHORT).show();

                }
                if (quantcheck){

                }

                else if(quantity <= 0){
                    Toast.makeText(getActivity(), "Please enter a Quantity that is greater then 0", Toast.LENGTH_SHORT).show();
                } else if (item_name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the name of the item", Toast.LENGTH_SHORT).show();
                } else if (expiration_date == null) {
                    Toast.makeText(getActivity(), "Please select an expiration date", Toast.LENGTH_SHORT).show();
                }
                else{
                    Item item_to_add= new Item(item_name,date_added,expiration_date,quantity);
                    SIListener.Add_Item(item_to_add);
                }


            }

        });


    }


    ExperationListner EListener;
    BackListner BListener;
    SaveItemListener SIListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EListener = (ExperationListner) context;
        BListener = (BackListner) context;
        SIListener = (SaveItemListener) context;
    }

    public interface ExperationListner{
        void GoToCalender();

    }
    public interface BackListner{
        void Back();

    }
    public interface SaveItemListener{
        void Add_Item(Item new_item);

    }
}