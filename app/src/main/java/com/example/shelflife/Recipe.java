package com.example.shelflife;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;


public class Recipe implements Serializable {
    private String Name;
    private URL Image;
    private int Likes;
    private ArrayList<RecipeItem> MissingIngredients;
    private ArrayList<RecipeItem> UsedIngredients;

    public Recipe(String name, URL image, int likes, ArrayList<RecipeItem> missingIngredients, ArrayList<RecipeItem> usedIngredients){
        this.Name = name;

        this.Image = image;
        this.Likes = likes;
        this.MissingIngredients = missingIngredients;
        this.UsedIngredients = usedIngredients;

    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }

    public URL getImage(){ return Image; }
    public void setImage(URL image){this.Image=image;}

    public int getLikes(){ return Likes; }
    public void setLikes(int likes){this.Likes = likes;}

    public ArrayList<RecipeItem> getMissingIngredients(){return MissingIngredients;}
    public void setMissingIngredients(ArrayList<RecipeItem> missingIngredients){this.MissingIngredients=missingIngredients;}

    public ArrayList<RecipeItem> getUsedIngredients(){return UsedIngredients;}
    public void setUsedIngredients(ArrayList<RecipeItem> usedIngredients){this.UsedIngredients=usedIngredients;}
}
