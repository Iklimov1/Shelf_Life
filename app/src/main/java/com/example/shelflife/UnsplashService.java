package com.example.shelflife;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashService {
    @GET("search/photos")
    Call<UnsplashResponse> searchFoodImage(
            @Query("query") String foodItem,
            @Query("client_id") String accessKey
    );
}
