package com.example.shelflife;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

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

    ArrayList<Item> itemlist_My_Pantry = new ArrayList<>();
    Item_Adapter adapter;
    ListView Item_List;

    public My_Pantry() {
        // Required empty public constructor
    }


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

        // Example ingredients, remove in production
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            itemlist_My_Pantry.add(new Item("apples",LocalDate.now(), LocalDate.now(), 4));
            itemlist_My_Pantry.add(new Item("flour",LocalDate.now(), LocalDate.now(), 4));
            itemlist_My_Pantry.add(new Item("sugar",LocalDate.now(), LocalDate.now(), 4));
        }

        // getRecipes method must be called from a separate thread, so that it doesn't throw android.os.NetworkOnMainThreadException
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Recipe> rs= getRecipes(itemlist_My_Pantry);
                for (Recipe r : rs) {
                    Log.d("recipe", r.getName());
                }
            }
        }).start();
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
       // loadItemList();

        adapter = new Item_Adapter(requireActivity(),R.layout.item_row_layout,itemlist_My_Pantry);
        Item_List.setAdapter(adapter);



        view.findViewById(R.id.Add_Item_Button).setOnClickListener(v -> IListener.GoToAdd_item());

        view.findViewById(R.id.Recipe_Button).setOnClickListener(v -> IListener.GoToRecipe_List(itemlist_My_Pantry));
        Item_List.setOnItemClickListener((parent, view1, position, id) -> {
            Item selection = itemlist_My_Pantry.get(position);
            IListener.gotoitemedit(selection);


        });



    }


    ItemListener IListener;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IListener = (ItemListener) context;


    }

    public void Add_item(Item newItem) {
        itemlist_My_Pantry.add(newItem);
        expiration_toast(itemlist_My_Pantry);
        saveItemList();
        adapter.notifyDataSetChanged();

    }

    public void Edit_item(Item oldItem, Item newItem) {
        itemlist_My_Pantry.remove(oldItem);
        itemlist_My_Pantry.add(newItem);
        expiration_toast(itemlist_My_Pantry);

    }

    public void delete_item(Item oldItem) {
        itemlist_My_Pantry.remove(oldItem);
        expiration_toast(itemlist_My_Pantry);
    }

    public interface ItemListener{
        void GoToAdd_item();
        void gotoitemedit(Item item);
        void GoToRecipe_List(ArrayList<Item> itemlist_My_Pantry);

    }

    public void expiration_toast(ArrayList<Item> Items_to_check){
        int checker = 0;
        for(int i = 0; i<Items_to_check.size();i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                checker = Items_to_check.get(i).getexpiration_date().compareTo(LocalDate.now());
            }
            if(checker <= 7){
                Toast.makeText(getActivity(), "Warning! " + Items_to_check.get(i).getName() + " is about to expire!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public boolean saveItemList(){
        Gson gson = new Gson();
        String json = gson.toJson(itemlist_My_Pantry);
        try{
            FileOutputStream fos = requireActivity().openFileOutput("pantry_items.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean loadItemList(){
        try{
            FileInputStream fis = requireActivity().openFileInput("pantry_items.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                jsonBuilder.append(line);
            }
            reader.close();
            String json = jsonBuilder.toString();
            Type type = new TypeToken<ArrayList<Item>>(){}.getType();
            itemlist_My_Pantry = new Gson().fromJson(json, type);


        }catch (FileNotFoundException e){
            e.printStackTrace();
            itemlist_My_Pantry = new ArrayList<>();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Recipe> getRecipes(ArrayList<Item> ingredients) {
        Log.d("recipe", "getRecipes: started");
        HttpURLConnection connection = null;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("https://api.spoonacular.com/recipes/findByIngredients?&number=6&ingredients=");
        for (Item i : ingredients) {
            queryBuilder.append(i.getName());
            queryBuilder.append(',');
        }
        queryBuilder.setLength(Math.max(queryBuilder.length() - 1, 0));

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        try {
            // Create connection
            URL url = new URL(queryBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.addRequestProperty("x-api-key", "304f6300c57f402e9a62018567e4c6f3");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(false);
            Log.d("recipe", "connecting to: " + queryBuilder.toString());
            connection.setRequestMethod("GET");
            connection.connect();

            Log.d("recipe", "request method: " + connection.getRequestMethod());
            Log.d("recipe", "response code: " + connection.getResponseCode());

            // Get Response
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream) connection.getContent());
            JsonElement root = JsonParser.parseReader(inputStreamReader);
            inputStreamReader.close();
            JsonArray list = root.getAsJsonArray();

            for (JsonElement el : list) {
                JsonObject o = el.getAsJsonObject();
                String name = o.get("title").getAsString();
                URL image = new URL(o.get("image").getAsString());
                int likes = o.get("likes").getAsInt();

                ArrayList<RecipeItem> missedIngredients = new ArrayList<RecipeItem>();
                JsonArray mis = o.get("missedIngredients").getAsJsonArray();
                for (JsonElement mi : mis) {
                    JsonObject i = mi.getAsJsonObject();
                    String i_name = i.get("name").getAsString();
                    URL i_image = new URL(i.get("image").getAsString());
                    int i_amount = i.get("amount").getAsInt();
                    String i_aisle = i.get("aisle").getAsString();
                    String i_unit = i.get("unitLong").getAsString();
                    String i_diplayName = i.get("original").getAsString();

                    missedIngredients.add(new RecipeItem(i_name, i_image, i_amount, i_aisle, i_unit, i_diplayName));
                }

                ArrayList<RecipeItem> usedIngredients = new ArrayList<RecipeItem>();
                JsonArray uis = o.get("usedIngredients").getAsJsonArray();
                for (JsonElement ui : uis) {
                    JsonObject i = ui.getAsJsonObject();
                    String i_name = i.get("name").getAsString();
                    URL i_image = new URL(i.get("image").getAsString());
                    int i_amount = i.get("amount").getAsInt();
                    String i_aisle = i.get("aisle").getAsString();
                    String i_unit = i.get("unitLong").getAsString();
                    String i_diplayName = i.get("original").getAsString();

                    usedIngredients.add(new RecipeItem(i_name, i_image, i_amount, i_aisle, i_unit, i_diplayName));
                }

                recipes.add(new Recipe(name, image, likes, missedIngredients, usedIngredients));
            }

        } catch (Exception e) {
            Log.d("recipe", "getRecipes: An Error Occured", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        return recipes;
    }

}