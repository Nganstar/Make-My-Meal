package com.company.android.make_my_meal.database;

/**
 * Created by akhma on 2017-11-24.
 */

public class RecipeDBSchema {
    public static final class RecipeTable {
        public static final String NAME = "Cards";

        public static final class Cols {
            public static final String MID = "mId";
            public static final String IMAGE = "mImage";
            public static final String TITLE = "mTitle";
            public static final String INGREDIENTS = "mIngredient";
            public static final String STEPS = "mSteps";
            public static final Boolean ISFAVOURITE = Boolean.valueOf("isFavourite");
        }
    }
}
