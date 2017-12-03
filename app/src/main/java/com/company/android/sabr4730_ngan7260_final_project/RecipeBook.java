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

    public void setFavRecipes(Recipe r, int state){
        //odd or remove recipe from favourite list
        r.setFavorite(state);
        if (state == 1){myFavRecipes.add(r);}
        else myFavRecipes.remove(getPosition(r));
    }
    public ArrayList<Recipe> getFavRecipes() {
        SQLiteDatabase database = this.getmDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Recipe Where ISFAVOURITE = 1", null);
        ArrayList<Recipe> recipeList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Recipe recipe = new Recipe();
                recipe.setImage(cursor.getString(1));
                recipe.setTitle(cursor.getString(2));
                recipe.setIngredients(cursor.getString(3));
                recipe.setSteps(cursor.getString(4));
                recipe.setFavorite(cursor.getInt(5));
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }


//        mContext = context.getApplicationContext();
//        mDatabase = new RecipeBaseHelper(mContext)
//                .getWritableDatabase();
//
//        //initialize if database is empty
//        String count = "SELECT * FROM Recipe Where ISFAVOURITE = 1";
//        Cursor mCursor = mDatabase.rawQuery(count, null);
//        mCursor.moveToFirst();
//        //mCursor.moveToFirst();


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
        String uuidString = r.getId();
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

        //initializes database
        Recipe r = new Recipe("shrimp_fried_rice", "SHRIMP FRIED RICE",
                "Ingredients:\n" +
                        "\n" +
                        "    3 tablespoons soy sauce\n" +
                        "    1 tablespoons sesame oil\n" +
                        "    1/2 teaspoon ginger powder\n" +
                        "    1/2 teaspoon white pepper\n" +
                        "    2 tablespoons olive oil\n" +
                        "    1 pound medium shrimp, peeled and deveined\n" +
                        "    Kosher salt and freshly ground black pepper, to taste\n" +
                        "    2 cloves garlic, minced\n" +
                        "    1 onion, diced\n" +
                        "    2 carrots, peeled and grated\n" +
                        "    1/2 cup frozen corn\n" +
                        "    1/2 cup frozen peas\n" +
                        "    3 cups cooked rice\n" +
                        "    2 green onions, sliced\n" +
                        "\n",
                "Instructions\n\n" +
                        "    In a small bowl, whisk together soy sauce, sesame oil, ginger powder and white pepper; set aside.\n" +
                        "    Heat olive oil in a large skillet or wok over medium high heat. Add shrimp, and cook, stirring occasionally, until pink, about 2-3 minutes; season with salt and pepper, to taste; set aside.\n" +
                        "    Add garlic and onion to the skillet, and cook, stirring often, until onions have become translucent, about 3-4 minutes. Stir in carrots, corn and peas, and cook, stirring constantly, until vegetables are tender, about 3-4 minutes.\n" +
                        "    Stir in rice, green onions and soy sauce mixture. Cook, stirring constantly, until heated through, about 2 minutes. Stir in shrimp.\n" +
                        "    Serve immediately.\n" +
                        "\n");
        addRecipe(r);

        r = new Recipe("garlic_sesame_noodle", "GARLIC SESAME NOODLE",
                "\n" +
                        "Directions:\n\n" +
                        "\n" +
                        "    2 (5.6-ounce) packages refrigerated Yaki-Soba, seasoning sauce packets discarded\n" +
                        "    1 tablespoon olive oil\n" +
                        "    1 pound flank steak, thinly sliced across the grain\n" +
                        "    1 red bell pepper, sliced\n" +
                        "    1 carrot, thinly sliced\n" +
                        "    1 head broccoli, cut into florets\n" +
                        "    1 green onion, thinly sliced\n" +
                        "    1/2 teaspoon sesame seeds\n" +
                        "\n" +
                        "For the sauce\n" +
                        "\n" +
                        "    1/4 cup reduced sodium soy sauce\n" +
                        "    4 cloves, garlic, minced\n" +
                        "    2 tablespoon browns sugar, packed\n" +
                        "    1 tablespoons sambal oelek (ground fresh chile paste), or more, to taste\n" +
                        "    1 tablespoon oyster sauce\n" +
                        "    1 tablespoon freshly grated ginger\n" +
                        "    1 teaspoon sesame oil\n" +
                        "\n",
                "Directions:\n\n" +

                        "    In a small bowl, whisk together soy sauce, garlic, brown sugar, sambal oelek, oyster sauce, ginger and sesame oil; set aside.\n" +
                        "    In a large pot of boiling water, add Yaki-Soba until loosened, about 1-2 minutes; drain well.\n" +
                        "    Heat olive oil in a large skillet over medium high heat. Add steak and cook, flipping once, until browned, about 3-4 minutes; set aside.\n" +
                        "    Stir in bell pepper and carrot to the skillet. Cook, stirring frequently, until tender, about 3-4 minutes.\n" +
                        "    Stir in Yaki-Soba, broccoli and soy sauce mixture until broccoli is tender and the sauce is slightly thickened, about 3-4 minutes.\n" +
                        "    Serve immediately, garnished with green onion and sesame seeds, if desired.\n" +
                        "\n" );
        addRecipe(r);
        r = new Recipe("sheet_pan_teriyaki_salmon", "SHEET PAN TERIYAKI SALMON",
                "\n" +
                        "Ingredients:\n" +
                        "\n" +
                        "    4 (5-ounce) salmon fillets\n" +
                        "    16 ounces green beans, trimmed\n" +
                        "    2 carrots, peeled and cut diagonally in 1/4-inch slices\n" +
                        "    1/2 cup teriyaki sauce, homemade or store-bought\n" +
                        "    2 tablespoons olive oil\n" +
                        "    Kosher salt and freshly ground black pepper, to taste\n" +
                        "    2 green onions, thinly sliced\n" +
                        "    1/2 teaspoon sesame seeds\n" +
                        "\n",
                "\n" +
                        "Directions:\n" +
                        "\n" +
                        "    Preheat oven to 400 degrees F. Lightly oil a baking sheet or coat with nonstick spray.\n" +
                        "    Place salmon, green beans and carrots in a single layer onto the prepared baking sheet.\n" +
                        "    Spoon teriyaki sauce over the salmon.\n" +
                        "    Drizzle green beans and carrots with olive oil; season with salt and pepper, to taste.\n" +
                        "    Place into oven and cook until the fish flakes easily with a fork, about 16-18 minutes.*\n" +
                        "    Serve immediately, garnished with green onions and sesame seeds, if desired.\n" +
                        "\n");
        addRecipe(r);

        r = new Recipe("one_pot_nacho_beef_skillet", "ONE POT NACHO BEEF SKILLET",
                "\n" +
                        "Ingredients:\n" +
                        "\n" +
                        "    1 tablespoon olive oil\n" +
                        "    8 ounces ground beef\n" +
                        "    3 cloves garlic, minced\n" +
                        "    1 onion, diced\n" +
                        "    1 cup basmati rice\n" +
                        "    1 (10-ounce can) Ro*Tel® Mild Diced Tomatoes & Green Chilies\n" +
                        "    1 cup corn kernels, frozen, canned or roasted\n" +
                        "    1/4 teaspoon chili powder\n" +
                        "    1/4 teaspoon cumin\n" +
                        "    Kosher salt and freshly ground black pepper, to taste\n" +
                        "    1 (12-ounce) jar roasted red peppers, drained and chopped\n" +
                        "    1/2 cup shredded cheddar cheese\n" +
                        "    1/2 cup shredded Monterey Jack cheese\n" +
                        "    1/4 cup crushed tortilla chips\n" +
                        "    1 Roma tomato, diced\n" +
                        "    2 tablespoons chopped fresh cilantro leaves\n" +
                        "\n",
                "\n" +
                        "Directions:\n" +
                        "\n" +
                        "    Heat olive oil in a large skillet over medium high heat. Add ground beef, garlic and onion. Cook until beef has browned, about 3-5 minutes, making sure to crumble the beef as it cooks; drain excess fat.\n" +
                        "    Stir in rice until toasted, about 2 minutes.\n" +
                        "    Stir in Ro*Tel® and 1 1/2 cups water, and bring to a simmer, about 2 minutes.\n" +
                        "    Stir in corn, chili powder and cumin; season with salt and pepper, to taste. Bring to a boil; cover, reduce heat and simmer until rice is cooked through, about 13-16 minutes.\n" +
                        "    Stir in red peppers until heated through, about 1 minute.\n" +
                        "    Remove from heat and top with cheeses. Cover until cheeses have melted, about 2 minutes.\n" +
                        "    Serve immediately, garnished with tortilla chips, tomato and cilantro, if desired.\n" +
                        "\n");
        addRecipe(r);

//        r = new Recipe("soccer", "What is your favourite sport?", "Soccer");
//        addRecipe(r);
    }


}
