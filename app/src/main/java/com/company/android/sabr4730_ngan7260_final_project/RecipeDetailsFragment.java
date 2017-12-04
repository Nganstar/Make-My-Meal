package com.company.android.sabr4730_ngan7260_final_project;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by akhma on 2017-12-02.
 */

public class RecipeDetailsFragment extends Fragment {
    private static final String THE_RECIPE_ID = "the_recipe_id";

    private Recipe mRecipe;
    private TextView mTitle;
    private ImageView mImage;
    private TextView mSteps;
    private TextView mIngerients;
    private CheckBox isFavourite;

    public static RecipeDetailsFragment newInstance(String receipeID) {
        Bundle args = new Bundle();
        args.putSerializable(THE_RECIPE_ID, receipeID);

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String recipeId = (String) getArguments().getSerializable(THE_RECIPE_ID);
        //Log.d("check","error: "+ recipeId);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(recipeId);
    }

    @Override
    public void onPause() { //ch 14
        super.onPause();

        RecipeBook.get(getActivity())
                .updateRecipe(mRecipe);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_details_fragment, container, false);

        mTitle = (TextView) view.findViewById(R.id.title_text_view);
        mTitle.setText(mRecipe.getTitle());
        mImage =(ImageView)view.findViewById(R.id.detail_image_view);


        Resources res = getResources();
        int resourceId = res.getIdentifier(mRecipe.getImage(), "drawable",
                getActivity().getPackageName());
        mImage.setImageResource(resourceId);

        isFavourite = (CheckBox)view.findViewById(R.id.favourite_checkBox);

        isFavourite.setChecked(mRecipe.getFavourite());
        isFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mRecipe.setFavourite(isChecked);

            }
        });

        mSteps = (TextView) view.findViewById(R.id.steps_text_view);
        mSteps.setText(mRecipe.getSteps());
        mIngerients=(TextView) view.findViewById(R.id.detail_ingredients_text_view);
        mIngerients.setText(mRecipe.getIngredients());
        RecipeBook.get(getActivity())
                .updateRecipe(mRecipe);

        return view;
    }
}
