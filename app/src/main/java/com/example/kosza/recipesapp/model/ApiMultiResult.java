package com.example.kosza.recipesapp.model;

import java.util.ArrayList;

/**
 * Created by kosza on 23.01.2016.
 */
public class ApiMultiResult {

    private Long count;
    private ArrayList<Recipe> recipes;

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
