package com.example.shelflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recipe_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recipe_List extends Fragment {


    ArrayList<Item> Items_to_check;
    ArrayList<Recipe> Recipes;
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
        Log.d("Recipe_List", "onViewCreated: " + Items_to_check);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Recipe> rs= getRecipes(Items_to_check);
                for (Recipe r : rs) {
                    Log.d("recipe", r.getName() + r.getImage());
                }
            }
        }).start();
        LinearLayout recipeContainer = view.findViewById(R.id.recipe_container_layout);  // ID of the LinearLayout inside your ScrollView

        new Thread(() -> {
            ArrayList<Recipe> rs = getRecipes(Items_to_check);

            requireActivity().runOnUiThread(() -> { // Switch to UI thread to update views
                LayoutInflater inflater = LayoutInflater.from(getContext());

                for (Recipe r : rs) {
                    View recipeItemView = inflater.inflate(R.layout.recipe_row_item, recipeContainer, false);

                    TextView recipeName = recipeItemView.findViewById(R.id.recipe_name);
                    ImageView recipeImage = recipeItemView.findViewById(R.id.recipe_image);
                    recipeItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = r.getImage().toString();
                            String title = r.getName();
                           String link =  r.generateLinkFromImage(url, title);
                            Log.d("LINK", "onClick: " + link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(intent);


                        }
                    });
                    String imageS = r.getImage().toString();
                    Log.d("IMG", imageS);

                    recipeName.setText(r.getName());
                    new Thread(() -> {
                        try {
                            InputStream in = r.getImage().openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(in); // decode in background
                            in.close(); // Close it after decoding

                            requireActivity().runOnUiThread(() -> {
                                recipeImage.setImageBitmap(bitmap); // Now just set the bitmap
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                    recipeContainer.addView(recipeItemView);
                }
            });
        }).start();







    }

    RecipeListener RListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RListener = (RecipeListener) context;
    }

    public interface RecipeListener{
        void Back();

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
            Log.d("recipe", "getRecipes: An Error Occurred", e);
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        return recipes;
    }
}