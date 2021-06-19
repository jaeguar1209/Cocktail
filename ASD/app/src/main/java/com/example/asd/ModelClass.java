package com.example.asd;


import java.util.ArrayList;
import java.util.List;

public class ModelClass {
    String strDrink;
    String strDrinkThumb;
    String strInstructions;
    List<String> ingredList;
    String strRecipe;



    public ModelClass(String strDrink, String strDrinkThumb, String strInstructions){//, String strRecipe) {
        this.strDrink = strDrink;
        this.strDrinkThumb = strDrinkThumb;
        this.strInstructions = strInstructions;
        ingredList = new ArrayList<String>();
        //this.strRecipe = strRecipe;
    }

    public ModelClass() {
        ingredList = new ArrayList<String>();
    }

    public String getStrDrink() {
        return strDrink;
    }

    public String getStrDrinkThumb() {
        return strDrinkThumb;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getIngredList() {
        StringBuilder recipe = new StringBuilder();
        for(String data : ingredList) {
            recipe.append(data);
            recipe.append("  ");
        }
        strRecipe = recipe.toString();
        return strRecipe;
    }

    public void setStrDrink(String strDrink) {
        this.strDrink = strDrink;
    }

    public void setStrDrinkThumb(String strDrinkThumb) {
        this.strDrinkThumb = strDrinkThumb;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public void AddingredList(String ingradient) {
        this.ingredList.add(ingradient);
    }

}