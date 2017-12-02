package com.company.android.sabr4730_ngan7260_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.android.sabr4730_ngan7260_final_project.database.RecipeBaseHelper;
import com.company.android.sabr4730_ngan7260_final_project.database.RecipeCursorWrapper;
import com.company.android.sabr4730_ngan7260_final_project.database.RecipeDBSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhma on 2017-11-23.
 */

public class RecipeBook {

    //atributes
    private static RecipeBook sRecipeBook; //ch 8
    private Context mContext;
    private List<Recipe> myFavRecipes;
    private List<Recipe> myRecipes;
    private List<Recipe> rssRecipes;

    private static SQLiteDatabase mDatabase; //ch14

    private RecipeBook(Context context) { //ch 8

        mContext = context.getApplicationContext();
        mDatabase = new RecipeBaseHelper(mContext)
                .getWritableDatabase();

        //initialize if database is empty
        String count = "SELECT count(*) FROM Recipe";
        Cursor mCursor = mDatabase.rawQuery(count, null);
        mCursor.moveToFirst();
        int iCount = mCursor.getInt(0);
        if(iCount==0){initialize();}
    }

    public void setFavRecipes(Recipe r, Boolean state){
        //odd or remove recipe from favourite list
        r.setFavorite(state);
        if (state == true){myFavRecipes.add(r);}
        else myFavRecipes.remove(getPosition(r));
    }

     public int getPosition(Recipe r){
        //get position of the recipe in the list
        int position = 0;
        while(!myFavRecipes.get(position).getId().equals(r.getId())){
            position++;
        }
        return position;
     }

    public static RecipeBook get(Context context) { //ch 8
        if (sRecipeBook == null) {
            sRecipeBook = new RecipeBook(context);
        }
        return sRecipeBook;
    }

    public List<Recipe> getRecipeBook() { //ch 8
        List<Recipe> recipeList = new ArrayList<>();

        RecipeCursorWrapper cursor = queryRecipe(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recipeList.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return recipeList;
    }

    public Recipe getRecipe(String id) {
        RecipeCursorWrapper cursor = queryRecipe(
                RecipeDBSchema.RecipeTable.Cols.MID + " = ?",
                new String[] { id }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRecipe();
        } finally {
            cursor.close();
        }
    }

    public void updateRecipe(Recipe r) {
        String uuidString = r.getId().toString();
        ContentValues values = getContentValues(r);

        mDatabase.update(RecipeDBSchema.RecipeTable.NAME, values,
                RecipeDBSchema.RecipeTable.Cols.MID + " = ?",
                new String[] { uuidString });
    }

    private RecipeCursorWrapper queryRecipe(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                RecipeDBSchema.RecipeTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new RecipeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Recipe r) {
        ContentValues values = new ContentValues();
        values.put(RecipeDBSchema.RecipeTable.Cols.MID, r.getId());
        values.put(RecipeDBSchema.RecipeTable.Cols.IMAGE, r.getImage());
        values.put(RecipeDBSchema.RecipeTable.Cols.TITLE, r.getTitle());
        values.put(RecipeDBSchema.RecipeTable.Cols.INGREDIENTS, r.getIngredients());
        values.put(RecipeDBSchema.RecipeTable.Cols.STEPS, r.getSteps());
        values.put(String.valueOf(RecipeDBSchema.RecipeTable.Cols.ISFAVOURITE), r.getFavourite());


        return values;
    }

    public void addRecipe(Recipe r) {
        ContentValues values = getContentValues(r);
        mDatabase.insert(RecipeDBSchema.RecipeTable.NAME, null, values);
    }

    public SQLiteDatabase getmDatabase(){
        return mDatabase;
    }

    void initialize(){

//        //initializes database
//        Recipe r = new Recipe("profile", "What is your name?", "Akhmad Sabri");
//        addRecipe(r);
//
//        r = new Recipe("laurier_logo", "What do you do?", "I am a student");
//        addRecipe(r);
//
//        r = new Recipe("indonesia1", "Where are you from?", "Indonesia");
//        addRecipe(r);
//
//        r = new Recipe("soccer", "What is your favourite sport?", "Soccer");
//        addRecipe(r);
    }


}
