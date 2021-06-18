package com.example.asd;

public class Cocktail {
    String name;
    int sugar;
    int alcohol;
    int body;
    int unique_;
    String base;
    int _id;
    public Cocktail(){}
    public Cocktail(String name, int sugar, int alcohol, int body, int unique_, String base,int _id){
        this.name=name;
        this.sugar=sugar;
        this.alcohol=alcohol;
        this.body=body;
        this.unique_=unique_;
        this.base=base;
        this._id=_id;
    }

    public int getId(){
        return _id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getSugar(){
        return sugar;
    }
    public int getAlcohol(){
        return alcohol;
    }
    public int getBody(){
        return body;
    }
    public int getUnique_(){
        return unique_;
    }
    public String getBase(){
        return base;
    }
}
