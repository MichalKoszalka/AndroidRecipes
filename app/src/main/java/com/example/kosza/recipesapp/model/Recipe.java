package com.example.kosza.recipesapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kosza on 23.01.2016.
 */
public class Recipe implements Serializable {

    private Long id;
    private String publisher;
    private ArrayList<String> ingredients;
    private String source_url;
    private String recipe_id;
    private String image_url;
    private String social_rank;
    private String publisher_url;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(String social_rank) {
        this.social_rank = social_rank;
    }

    public String getPublisher_url() {
        return publisher_url;
    }

    public void setPublisher_url(String publisher_url) {
        this.publisher_url = publisher_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
