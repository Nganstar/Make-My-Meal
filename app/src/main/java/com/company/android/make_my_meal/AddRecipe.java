package com.company.android.make_my_meal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by akhma on 2017-11-23.
 */

public class AddRecipe extends AppCompatActivity {

    public static final String CARD_PASSWORD = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }
    public void CreateCard(View view){

        //extracts data from editTexts
        EditText imageEditText = (EditText) findViewById(R.id.image_text_edit);
        EditText titleEditText = (EditText) findViewById(R.id.title_text_edit);
        EditText stepsEditText = (EditText) findViewById(R.id.steps_text_edit);
        EditText ingredientEditText = (EditText) findViewById(R.id.ingredients_text_edit);

        //convert data to string
        String imageText = imageEditText.getText().toString();
        String titleText = titleEditText.getText().toString();
        String ingredientsText = ingredientEditText.getText().toString();
        String stepsText = stepsEditText.getText().toString();

        //create card
        Recipe r = new Recipe(imageText, titleText, ingredientsText, stepsText);
        RecipeBook.get(this).addRecipe(r);

        //make toast to indicate that card has been added
        Toast.makeText(this, "Recipe has been added", Toast.LENGTH_SHORT).show();

    }
}

