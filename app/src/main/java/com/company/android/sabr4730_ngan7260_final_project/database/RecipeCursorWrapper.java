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
        Boolean isfavourite = Boolean.getBoolean(String.valueOf(RecipeDBSchema.RecipeTable.Cols.ISFAVOURITE));

        Recipe r = new Recipe(image, title, ingredients, steps);
        return r;
    }
}
