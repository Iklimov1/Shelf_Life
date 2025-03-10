package com.example.shelflife;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements HomePage.WelcomeListner, My_Pantry.ItemListner,
Add_Item.BackListner,Add_Item.ExperationListner,Calender.BackListner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void GoToMy_Pantry() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new My_Pantry(),"main")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void GoToAdd_item() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new Add_Item(),"Adding Item")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void Back() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void GoToCalender() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new Calender())
                .addToBackStack(null)
                .commit();

    }
}