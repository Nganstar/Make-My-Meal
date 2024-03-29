package com.company.android.sabr4730_ngan7260_final_project.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.company.android.sabr4730_ngan7260_final_project.Recipe;


/**
 * Created by akhma on 2017-11-24.
 */

public class RecipeCursorWrapper extends CursorWrapper {
    public RecipeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Recipe getRecipe() {
        String mid = getString(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.MID));
        String image = getString(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.IMAGE));
        String title = getString(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.TITLE));
        String ingredients = getString(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.INGREDIENTS));
        String steps = getString(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.STEPS));
        int isfavorite = getInt(getColumnIndex(RecipeDBSchema.RecipeTable.Cols.ISFAVOURITE));

        Recipe r = new Recipe(mid);
        r.setImage(image);
        r.setIngredients(ingredients);
        r.setTitle(title);
        r.setSteps(steps);
        //r.setFavourite(isfavorite);
        r.setFavourite(isfavorite != 0);

        return r;
    }
}
