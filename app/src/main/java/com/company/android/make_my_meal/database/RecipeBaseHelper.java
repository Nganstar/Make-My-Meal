package com.company.android.make_my_meal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akhma on 2017-11-24.
 */

public class RecipeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recipeBook.db";

    public RecipeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RecipeDBSchema.RecipeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                RecipeDBSchema.RecipeTable.Cols.MID + ", " +
                RecipeDBSchema.RecipeTable.Cols.IMAGE + ", " +
                RecipeDBSchema.RecipeTable.Cols.TITLE + ", " +
                RecipeDBSchema.RecipeTable.Cols.INGREDIENTS + ", " +
                RecipeDBSchema.RecipeTable.Cols.STEPS + ", " +
                RecipeDBSchema.RecipeTable.Cols.ISFAVOURITE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

