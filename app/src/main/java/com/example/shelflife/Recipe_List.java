package com.example.shelflife;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recipe_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recipe_List extends Fragment {


    ArrayList<Item> Items_to_check;

    public Recipe_List() {
        // Required empty public constructor
    }


    public static Recipe_List newInstance(ArrayList<Item> itemlist_My_Pantry) {
        Recipe_List fragment = new Recipe_List();
        Bundle args = new Bundle();
        args.putSerializable("ARG_PARAM_userinfo", itemlist_My_Pantry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Items_to_check = (ArrayList<Item>) getArguments().getSerializable("ARG_PARAM_userinfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe__list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.Back_Recipe_list_Button).setOnClickListener(v -> RListener.Back());

    }

    RecipeListener RListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RListener = (RecipeListener) context;
    }

    public interface RecipeListener{
        void Back();

    }
}