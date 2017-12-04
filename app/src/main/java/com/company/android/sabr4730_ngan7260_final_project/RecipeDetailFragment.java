package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by akhma on 2017-11-17.
 */

public class RecipeDetailFragment extends Fragment {
    private static final String RECIPE_ID = "recipeID";
    public static final String EXTRA_MESSAGE = "link";


    private Recipe mRecipe;
    private TextView mTitleTextView;
    private ImageView mImageView;
    private Button mLinkButton;
    public View view;

    public static RecipeDetailFragment newInstance(ArrayList<String> list) {

        Bundle args = new Bundle();
        args.putStringArrayList(RECIPE_ID, list);
        Log.d("new","ARG: "+ list);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ArrayList<String> list =  getArguments().getStringArrayList(RECIPE_ID);
        Log.d("new","ARG: "+ list.get(0));
        mRecipe = new Recipe(list.get(0),list.get(1),"food","food","0");
        mRecipe.setURL(list.get(2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recipe_feed_details, container, false);

        mTitleTextView = (TextView) view.findViewById(R.id.description_text_view);
        mTitleTextView.setText(mRecipe.getTitle());

        Resources res = getResources();
        if (mRecipe.getImage() != null) {
            int resourceId = res.getIdentifier(mRecipe.getImage(), "drawable", getActivity().getPackageName());
            ImageView image = (ImageView) view.findViewById(R.id.recipe_image_view);
            image.setImageResource(resourceId);
        }

        mLinkButton = (Button) view.findViewById(R.id.link_button);
        mLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                String message = mRecipe.getURL();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        mImageView = (ImageView) view.findViewById(R.id.recipe_image_view);

        Picasso.with(getActivity().getApplicationContext())
                .load(mRecipe.getImage())
                .error(R.drawable.profile)
                .into(mImageView);

        RecipeBook.get(getActivity());
        return view;
    }
}