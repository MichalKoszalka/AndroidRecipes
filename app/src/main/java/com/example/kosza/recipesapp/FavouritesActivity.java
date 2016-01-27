package com.example.kosza.recipesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kosza.recipesapp.database.DBAdapter;
import com.example.kosza.recipesapp.model.Recipe;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private DBAdapter dbAdapter;
    private ListView favouritesListView;
    private RecipesAdapter adapter;
    private List<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favouritesListView = (ListView)findViewById(R.id.favourites_listView);
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        recipes = dbAdapter.getAllRecipes();
        dbAdapter.close();
        adapter = new RecipesAdapter(this, recipes);
        favouritesListView.setAdapter(adapter);
        favouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavouritesActivity.this, RecipeDetailsActivity.class);
                intent.putExtra(MainActivity.RECIPE, recipes.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        dbAdapter.open();
        recipes = dbAdapter.getAllRecipes();
        dbAdapter.close();
        adapter.recipes.clear();
        adapter.recipes.addAll(recipes);
        favouritesListView.setAdapter(adapter);
    }

}
