package com.company.android.make_my_meal;

import java.util.UUID;

/**
 * Created by akhma on 2017-11-23.
 */

public class Recipe{

    private String mId;
    private String mImage;
    private String mTitle;
    private String mIngredients;
    private String mSteps;
    private Boolean isFavourite;

    //constructors
    public Recipe(String image, String title,  String ingredients, String steps) {
        this(UUID.randomUUID().toString());
        this.mImage = image;
        this.mTitle = title;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.isFavourite = false;
    }

    public Recipe(){};

    public Recipe(String id) {}

    //methods

    public String getImage() {
        return mImage;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public String getSteps() {
        return mSteps;
    }

    public String getTitle() {
        return mTitle;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public String getId() {
        return mId;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public void setSteps(String steps) {
        mSteps = steps;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setFavorite(Boolean state) {
        isFavourite = state;
    }
}
