package com.example.shelflife;

import java.io.Serializable;
import java.time.LocalDate;


public class Item implements Serializable {
    private String Name;
    private double quantity;
    private LocalDate date_added;
    private LocalDate expiration_date;
    private String Comment;
    private  String imageUrl;

    public Item(String name, LocalDate date_added, LocalDate expiration_date,double quantity ){
        this.Name = name;

        this.date_added = date_added;
        this.expiration_date = expiration_date;
        this.quantity = quantity;

    }
    public Item(String name, LocalDate date_added, LocalDate expiration_date,double quantity,String imageUrl){
        this.imageUrl=imageUrl;
        this.Name = name;
        this.date_added = date_added;
        this.expiration_date = expiration_date;
        this.quantity = quantity;
        this.Comment=Comment;

    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName(){
        return this.Name;
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
    public double getquantity(){return quantity;}
    public void setquantity(double quantity){this.quantity = quantity;}

    public String toString(){
        return "Item{" +
                "Name='" + Name + '\'' +
                ", quantity=" + quantity +
                ", date_added=" + date_added +
                ", expiration_date=" + expiration_date +
                ", Comment='" + Comment + '\'' +
                '}';
    }


}
