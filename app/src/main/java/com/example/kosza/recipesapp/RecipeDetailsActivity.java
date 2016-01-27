package com.example.kosza.recipesapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kosza.recipesapp.database.DBAdapter;
import com.example.kosza.recipesapp.model.ApiMultiResult;
import com.example.kosza.recipesapp.model.ApiSingleResult;
import com.example.kosza.recipesapp.model.Recipe;
import com.example.kosza.recipesapp.rest.ImageDownloader;
import com.example.kosza.recipesapp.rest.RecipesController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe recipe;
    private DBAdapter dbAdapter;
    private Cursor recipeCursor;
    public static final String SOURCE_URL = "source_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        recipe = (Recipe) getIntent().getSerializableExtra(MainActivity.RECIPE);
        dbAdapter = new DBAdapter(this);
        RecipesController.Food2Work controller = RecipesController.getClient();
        System.out.println("getting client and calling recipes " + recipe.getRecipe_id());
        dbAdapter.open();
        Recipe r = dbAdapter.getRecipe(recipe.getRecipe_id());
        dbAdapter.close();
        if(r != null) {
            ((Button)findViewById(R.id.add_to_favourites)).setEnabled(false);
            ((Button)findViewById(R.id.delete_from_favourites)).setEnabled(true);
        }
        Call<ApiSingleResult> call = controller.getRecipe(RecipesController.AUTH_KEY, recipe.getRecipe_id());


        call.enqueue(new Callback<ApiSingleResult>() {
            @Override
            public void onResponse(Response<ApiSingleResult> response) {
                if (response.isSuccess()) {
                    recipe = response.body().getRecipe();
                    if (recipe.getImage_url() != null) {
                        new ImageDownloader((ImageView) findViewById(R.id.image)).execute(recipe.getImage_url());
                    }
                    if (recipe.getPublisher() != null) {
                        ((TextView) findViewById(R.id.publisher)).setText(recipe.getPublisher());
                    }
                    if (recipe.getTitle() != null) {
                        ((TextView) findViewById(R.id.title)).setText(recipe.getTitle());
                    }
                    if (recipe.getSocial_rank() != null) {
                        ((TextView) findViewById(R.id.social_rank)).setText(recipe.getSocial_rank().toString());
                    }
                    if (recipe.getSource_url() != null) {
                        ((TextView) findViewById(R.id.source_url)).setText(recipe.getSource_url());
                        ((TextView) findViewById(R.id.source_url)).setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    if (recipe.getPublisher() != null) {
                        ((TextView) findViewById(R.id.publisher)).setText(recipe.getPublisher());
                    }
                    if (recipe.getIngredients() != null) {
                        ArrayAdapter adapter = new ArrayAdapter<String>(RecipeDetailsActivity.this, android.R.layout.simple_list_item_1, recipe.getIngredients());
                        ((ListView) findViewById(R.id.ingredients)).setAdapter(adapter);
                    }
                } else {
                    System.out.println("Error getting data from api");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failure " + t.getMessage());
            }
        });

        ((Button)findViewById(R.id.add_to_favourites)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.open();
                dbAdapter.insertRecipe(recipe);
                dbAdapter.close();
                ((Button) findViewById(R.id.add_to_favourites)).setEnabled(false);
                ((Button) findViewById(R.id.delete_from_favourites)).setEnabled(true);
            }
        });
        ((Button)findViewById(R.id.delete_from_favourites)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.open();
                dbAdapter.deleteRecipe(recipe.getRecipe_id());
                dbAdapter.close();
                ((Button) findViewById(R.id.add_to_favourites)).setEnabled(true);
                ((Button) findViewById(R.id.delete_from_favourites)).setEnabled(false);
            }
        });
        findViewById(R.id.source_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailsActivity.this, WebActivity.class);
                intent.putExtra(SOURCE_URL, recipe.getSource_url());
            }
        });


        }
}
