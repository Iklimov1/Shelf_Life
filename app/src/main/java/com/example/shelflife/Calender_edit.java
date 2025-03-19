package com.example.shelflife;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calender_edit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calender_edit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CalendarView calendar;
    LocalDate expiration_date;

    public Calender_edit() {
        // Required empty public constructor
    }


    public Calender_edit newInstance(LocalDate date) {
        Calender_edit fragment = new Calender_edit();
        Bundle args = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            args.putSerializable("ARG_PARAM_userinfo", date);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                expiration_date = (LocalDate) getArguments().getSerializable("ARG_PARAM_userinfo");
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender2, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = view.findViewById(R.id.calendarView2);
        calendar.setMinDate(System.currentTimeMillis() - 1000);



        view.findViewById(R.id.Calender_Back_Button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CEListener.Back();

            }

        });



        view.findViewById(R.id.Calender_Set_Date_Button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CEListener.Send_new_date(expiration_date);



            }

        });


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    expiration_date = LocalDate.of(year, month+1, dayOfMonth);
                }
            }
        });


    }


    CalenderEditListener CEListener;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        CEListener = (CalenderEditListener) context;

    }




    public interface CalenderEditListener{
        void Back();
        void Send_new_date(LocalDate date);

    }


}