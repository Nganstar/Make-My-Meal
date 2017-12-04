package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhma on 2017-12-02.
 */

public class FavouritesFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPause() { //ch 14
        super.onPause();

        updateUI();
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_recipe_list, container, false);
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        updateUI();
    }

    private void updateUI() {
        RecipeBook recipeBook = RecipeBook.get(getActivity());
        List<Recipe> mRecipeList = recipeBook.getRecipeBook();
        List<Recipe> mFavouriteRecipeList = new ArrayList<>();
        for(int i =0; i<mRecipeList.size(); i++){
            if(mRecipeList.get(i).getFavourite() == true){
                mFavouriteRecipeList.add(mRecipeList.get(i));
            }
        }

        if (mAdapter == null) {
  //          mAdapter = new RecipeAdapter(mRecipeList);
            mAdapter = new RecipeAdapter(mFavouriteRecipeList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            //mAdapter.setRecipeList(mRecipeList);
            mAdapter.setRecipeList(mFavouriteRecipeList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Recipe mRecipe;

        private TextView mRecipeTextView;
        private ImageView mImageView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recipe_list, parent, false));
            itemView.setOnClickListener(this);

            mRecipeTextView = (TextView) itemView.findViewById(R.id.description_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
        }

        public void bind(Recipe r) {
            mRecipe = r;
            mRecipeTextView.setText(mRecipe.getTitle());
            Resources res = getResources();
            int resourceId = res.getIdentifier(mRecipe.getImage(),"drawable",getActivity().getPackageName());
            mImageView.setImageResource(resourceId);

        }

        @Override
        public void onClick(View view) {
            Log.d("check","error: "+ mRecipe.getId());
            Intent intent = DetailActivity.newIntent(getActivity(), mRecipe.getId());
            startActivity(intent);
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private List<Recipe> mRecipeList;

        public RecipeAdapter(List<Recipe> recipeList) {
            mRecipeList = recipeList;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            Log.d("check","error: "+ mRecipeList.get(position));
            Recipe r = mRecipeList.get(position);
            holder.bind(r);
        }

        public void remove(int position){
            RecipeBook.get(getActivity()).getmDatabase().execSQL("delete from " + "Recipe" + " where MID='" + (mRecipeList.get(position).getId()) + "'");
            mRecipeList.remove(position);
        }

        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }

        public void setRecipeList(List<Recipe> recipe) {
            mRecipeList = recipe;
        }
    }

}
