package com.example.kosza.recipesapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kosza.recipesapp.model.ApiMultiResult;
import com.example.kosza.recipesapp.model.Recipe;
import com.example.kosza.recipesapp.rest.RecipesController;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    public static final String RECIPE= "recipe";
    private ListView list;
    private RecipesAdapter adapter;
    private List<Recipe> recipes;
    private final RecipesController.Food2Work controller = RecipesController.getClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AppCompatButton)findViewById(R.id.favourites)).setOnClickListener(new AppCompatButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

        list = (ListView)findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                intent.putExtra(RECIPE, recipes.get(position));
                startActivity(intent);
            }
        });

        Call<ApiMultiResult> call = controller.getRecipes(RecipesController.AUTH_KEY, "");

        ((SearchView)findViewById(R.id.search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<ApiMultiResult> call = controller.getRecipes(RecipesController.AUTH_KEY, query);
                call.enqueue(new Callback<ApiMultiResult>() {
                    @Override
                    public void onResponse(Response<ApiMultiResult> response) {
                        if (response.isSuccess()) {
                            recipes = response.body().getRecipes();
                            adapter.recipes.clear();
                            adapter.recipes.addAll(recipes);
                            list.setAdapter(adapter);
                        } else {
                            System.out.println("Error getting data from api");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("Failure " + t.getMessage());
                    }
                });
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        call.enqueue(new Callback<ApiMultiResult>() {
            @Override
            public void onResponse(Response<ApiMultiResult> response) {
                if (response.isSuccess()) {
                    recipes = response.body().getRecipes();
                    adapter = new RecipesAdapter(MainActivity.this, recipes);
                    list.setAdapter(adapter);
                } else {
                    System.out.println("Error getting data from api");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failure " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
