package com.company.android.make_my_meal;

import android.content.Context;

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

    public void setFavRecipes(Recipe r, Boolean state){
        r.setFavorite(state);
        if (state == true){myFavRecipes.add(r);}
        else myFavRecipes.remove(getPosition(r));
    }

     public int getPosition(Recipe r){
        int position = 0;
        while(!myFavRecipes.get(position).getId().equals(r.getId())){
            position++;
        }
        return position;
     }

}
