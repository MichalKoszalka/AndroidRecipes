package com.example.kosza.recipesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kosza.recipesapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosza on 26.01.2016.
 */
public class DBAdapter {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String RECIPE_TABLE_NAME = "recipe";
    private static final String RECIPE_TABLE_CREATE =
            "CREATE TABLE " + RECIPE_TABLE_NAME + "( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, publisher TEXT NOT NULL, source_url TEXT NOT NULL, recipe_id TEXT NOT NULL UNIQUE," +
                    "image_url TEXT NOT NULL, social_rank TEXT NOT NULL, publisher_url TEXT NOT NULL, title TEXT NOT NULL)";

    private static final String RECIPE_TABLE_DROP =
            "DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME;

    private SQLiteDatabase db;
    private Context context;
    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter open() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        }
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put("publisher", recipe.getPublisher());
        values.put("image_url", recipe.getImage_url());
        values.put("publisher_url", recipe.getPublisher_url());
        values.put("recipe_id", recipe.getRecipe_id());
        values.put("social_rank", recipe.getSocial_rank());
        values.put("source_url", recipe.getSource_url());
        values.put("title", recipe.getTitle());
        return db.insert(RECIPE_TABLE_NAME, null, values);
    }

    public boolean deleteRecipe(String id) {
        String where = "recipe_id" + "=" + id;
        return db.delete(RECIPE_TABLE_NAME, where, null) > 0;
    }

    public List<Recipe> getAllRecipes() {
        String[] columns = {"id", "publisher", "image_url", "publisher_url", "recipe_id", "social_rank", "source_url", "title"};
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = db.query(RECIPE_TABLE_NAME, columns, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                Recipe r = new Recipe();
                r.setImage_url(cursor.getString(2));
                r.setPublisher(cursor.getString(1));
                r.setPublisher_url(cursor.getString(3));
                r.setRecipe_id(cursor.getString(4));
                r.setSocial_rank(cursor.getString(5));
                r.setSource_url(cursor.getString(6));
                r.setTitle(cursor.getString(7));
                recipes.add(r);
            } while(cursor.moveToNext());
        }
        return recipes;
    }

    public Recipe getRecipe(String id) {
        String[] columns = {"id", "publisher", "image_url", "publisher_url", "recipe_id", "social_rank", "source_url", "title"};
        String where = "recipe_id" + "=" + id;
        Cursor cursor = db.query(RECIPE_TABLE_NAME, columns, where, null, null, null, null);
        Recipe recipe = null;
        if(cursor != null && cursor.moveToFirst()) {
            recipe = new Recipe();
            recipe.setImage_url(cursor.getString(2));
            recipe.setPublisher(cursor.getString(1));
            recipe.setPublisher_url(cursor.getString(3));
            recipe.setRecipe_id(cursor.getString(4));
            recipe.setSocial_rank(cursor.getString(5));
            recipe.setSource_url(cursor.getString(6));
            recipe.setTitle(cursor.getString(7));
        }
        return recipe;
    }

    private static class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(RECIPE_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(RECIPE_TABLE_DROP);
            db.execSQL(RECIPE_TABLE_CREATE);
        }
    }
}

