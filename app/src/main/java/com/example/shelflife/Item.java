package com.example.shelflife;

import java.io.Serializable;
import java.time.LocalDate;


public class Item implements Serializable {
    private String Name;
    private float quantity;
    private LocalDate date_added;
    private LocalDate expiration_date;
    private String Comment;

    public Item(String name, LocalDate date_added, LocalDate expiration_date,float quantity ){
        this.Name = name;

        this.date_added = date_added;
        this.expiration_date = expiration_date;
        this.quantity = quantity;

    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public String getComment(){
        return Comment;
    }
    public void setComment(String Comment){
        this.Comment=Comment;
    }
    public LocalDate getexpiration_date(){return expiration_date;}
    public void setexpiration_date(LocalDate expiration_date){this.expiration_date= expiration_date;}
    public LocalDate getdate_added(){return date_added;}
    public float getquantity(){return quantity;}
    public void setquantity(float quantity){this.quantity = quantity;}


}
