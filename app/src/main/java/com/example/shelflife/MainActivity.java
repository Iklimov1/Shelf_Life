package com.example.shelflife;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomePage.WelcomeListener, My_Pantry.ItemListener,
Add_Item.BackListener,Add_Item.ExpirationListener, Calender.BackListener,Recipe_List.RecipeListener,
        Recipe_Output.BackListener,Calender.DateSubmitListener,Add_Item.SaveItemListener,Edit_Item.EditListener,
        Calender_edit.CalenderEditListener
{
    //ArrayList<Item> itemlist = new ArrayList<Item>();

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
    public void gotoitemedit(Item item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new Edit_Item().newInstance(item),"Editing Item")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void Back() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void Send_new_date(LocalDate date) {
        Edit_Item fragment = (Edit_Item) getSupportFragmentManager().findFragmentByTag("Editing Item");

        if(fragment != null){
            fragment.setDate(date);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void to_edit_Calender(LocalDate new_date) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new Calender_edit().newInstance(new_date))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void edit_item(Item old_item, Item new_item) {
        My_Pantry fragment = (My_Pantry) getSupportFragmentManager().findFragmentByTag("main");

        if(fragment != null){
            fragment.Edit_item(old_item,new_item);
        }
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void delete_item(Item old_item) {
        My_Pantry fragment = (My_Pantry) getSupportFragmentManager().findFragmentByTag("main");

        if(fragment != null){
            fragment.delete_item(old_item);
        }
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

    @Override
    public void GoToRecipe_List(ArrayList<Item> itemlist_My_Pantry) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,new Recipe_List().newInstance(itemlist_My_Pantry))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void Submit_Expiration_date(LocalDate expiration_date) {
        Add_Item fragment = (Add_Item) getSupportFragmentManager().findFragmentByTag("Adding Item");

        if(fragment != null){
            fragment.setDate(expiration_date);
        }
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void Add_Item(Item new_item) {
        //itemlist.add(new_item);
        My_Pantry fragment = (My_Pantry) getSupportFragmentManager().findFragmentByTag("main");

        if(fragment != null){
            fragment.Add_item(new_item);
        }
        getSupportFragmentManager().popBackStack();

    }
}