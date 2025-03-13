package com.example.shelflife;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link My_Pantry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_Pantry extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Item> itemlist_My_Pantry = new ArrayList<Item>();
    Item_Adapter adapter;
    ListView Item_List;

    public My_Pantry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment My_Pantry.
     */
    // TODO: Rename and change types and number of parameters
    public static My_Pantry newInstance(String param1, String param2) {
        My_Pantry fragment = new My_Pantry();
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
        return inflater.inflate(R.layout.fragment_my__pantry, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Item_List = view.findViewById(R.id.listView);

        adapter = new Item_Adapter(getActivity(),R.layout.item_row_layout,itemlist_My_Pantry);
        Item_List.setAdapter(adapter);



        view.findViewById(R.id.Add_Item_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IListener.GoToAdd_item();

            }

        });

        view.findViewById(R.id.Recipe_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IListener.GoToRecipe_List();

            }

        });
        Item_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selection = itemlist_My_Pantry.get(position);
                IListener.gotoitemedit(selection);


            }
        });



    }


    ItemListner IListener;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IListener = (ItemListner) context;


    }

    public void Add_item(Item newItem) {
        itemlist_My_Pantry.add(newItem);
    }

    public void Edit_item(Item oldItem, Item newItem) {
        itemlist_My_Pantry.remove(oldItem);
        itemlist_My_Pantry.add(newItem);

    }

    public interface ItemListner{
        void GoToAdd_item();
        void gotoitemedit(Item item);
        void GoToRecipe_List();

    }


}