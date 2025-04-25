package com.example.shelflife;

import java.util.List;

public class UnsplashResponse {
    public List<UnsplashPhoto> results;

    public static class UnsplashPhoto {
        public UnsplashUrls urls;
    }

    public static class UnsplashUrls {
        public String small;  // URL of the small-sized image
    }
}
