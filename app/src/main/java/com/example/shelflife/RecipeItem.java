package com.example.shelflife;

import java.net.URL;

public class RecipeItem {
    private String Aisle;
    private int Amount;
    private String Unit;
    private String Name;
    private URL Image;
    private String DisplayName;

    public RecipeItem(String name, URL image, int amount, String aisle, String unit, String displayName){
        this.Name = name;

        this.Image = image;
        this.Aisle = aisle;
        this.Amount = amount;
        this.Unit = unit;
        this.DisplayName = displayName;
    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }

    public URL getImage(){ return Image; }
    public void setImage(URL image){this.Image=image;}

    public String getAisle(){return this.Aisle;}
    public void setAisle(String aisle){this.Aisle = aisle;}

    public int getAmount(){return this.Amount;}
    public void setAmount(int amount){this.Amount = amount;}

    public String getUnit(){return this.Unit;}
    public void setUnit(String unit){this.Unit = unit;}

    public String getDisplayName(){return this.DisplayName;}
    public void setDisplayName(String displayName){this.DisplayName = displayName;}
}
