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
import android.widget.CalendarView;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calender#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calender extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    CalendarView calendar;
    LocalDate expiration_date;

    public Calender() {
        // Required empty public constructor
    }


    public static Calender newInstance(String param1, String param2) {
        Calender fragment = new Calender();
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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = view.findViewById(R.id.calendarView);
        calendar.setMinDate(System.currentTimeMillis() - 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            expiration_date=LocalDate.now();
        }


        view.findViewById(R.id.Calender_Back_Button).setOnClickListener(v -> BListener.Back());

        view.findViewById(R.id.Calender_Set_Date_Button).setOnClickListener(v -> DSListener.Submit_Experation_date(expiration_date));


        calendar.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                expiration_date = LocalDate.of(year, month+1, dayOfMonth);
            }
        });


    }


    BackListner BListener;
    DateSubmitListener DSListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        BListener = (BackListner) context;
        DSListener = (DateSubmitListener) context;
    }




    public interface BackListner{
        void Back();

    }

    public interface DateSubmitListener{
        void Submit_Experation_date(LocalDate expiration_date);

    }
}