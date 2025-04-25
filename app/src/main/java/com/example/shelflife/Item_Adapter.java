package com.example.shelflife;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Item_Adapter extends ArrayAdapter<Item> {
    private static final String UNSPLASH_API_KEY = "iX-lbMVljKd1tVNr9eGrPHrdIg9FSLmBF5MliddFyK8";
    private static final String BASE_URL = "https://api.unsplash.com/";
    private final HashMap<String, String> imageUrlCache = new HashMap<>();
    public Item_Adapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_row_layout,parent,false);
        }



        Item item = getItem(position);
        TextView Item_name = convertView.findViewById(R.id.item_output);
        ImageView item_image = convertView.findViewById(R.id.item_image);
        assert item != null;
        String foodName = item.getName().toLowerCase();
        fetchFoodImage(foodName, item_image);

        TextView expiration_date = convertView.findViewById(R.id.expiration_date);
        String eDate = item.getexpiration_date().toString();
        assert item != null;
        Item_name.setText(item.getName());

        expiration_date.setText(eDate);



        return convertView;
    }
    public String formatIngredientName(String name) {
        return name.trim().toLowerCase().replace(" ", "-");
    }


    private void fetchFoodImage(String foodName, ImageView imageView) {

        if (imageUrlCache.containsKey(foodName)) {
            Glide.with(getContext())
                    .load(imageUrlCache.get(foodName))
                    .placeholder(R.drawable.placeholder)
                    .override(150,150)
                    .centerCrop()
                    .into(imageView);
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UnsplashService service = retrofit.create(UnsplashService.class);
        Call<UnsplashResponse> call = service.searchFoodImage(foodName, UNSPLASH_API_KEY);

        // Make the API request
        call.enqueue(new Callback<UnsplashResponse>() {
            @Override
            public void onResponse(Call<UnsplashResponse> call, Response<UnsplashResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().results.isEmpty()) {
                    String imageUrl = response.body().results.get(0).urls.small;
                    imageUrlCache.put(foodName, imageUrl);  // Cache the image URL
                    Glide.with(getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.placeholder)
                            .override(150,150)
                            .centerCrop()
                            .into(imageView);
                } else {
                    Glide.with(getContext())
                            .load(R.drawable.placeholder)
                            .override(150,150)
                            .centerCrop()
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(Call<UnsplashResponse> call, Throwable t) {
                Log.e("Item_Adapter", "Failed to fetch image: " + t.getMessage());
                // Fallback to placeholder image in case of failure
                Glide.with(getContext())
                        .load(R.drawable.placeholder)
                        .override(150,150)
                        .centerCrop()
                        .into(imageView);
            }
        });
    }
}
