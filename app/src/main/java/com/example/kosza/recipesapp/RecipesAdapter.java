package com.example.kosza.recipesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kosza.recipesapp.model.Recipe;

import java.util.List;

/**
 * Created by kosza on 26.01.2016.
 */
public class RecipesAdapter extends BaseAdapter {

    List<Recipe> recipes;
    private Context context;

    public RecipesAdapter(Context context, List<Recipe> recipes) {
        super();
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return this.recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.cell, parent, false);
        TextView tv = (TextView) v.findViewById(R.id.textview);
        Recipe recipe = recipes.get(position);
        tv.setText(recipe.getTitle());
        return v;
    }
}
