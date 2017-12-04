package com.company.android.sabr4730_ngan7260_final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akhma on 2017-12-02.
 */

public class AllRecipesFragment extends Fragment {
    private RecyclerView mCardListRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_article_list, container, false);

        mCardListRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mCardListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //remove interface from the assignment page
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe
                if (swipeDir == ItemTouchHelper.LEFT) { //if swipe left
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?"); //set message
                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position); //item removed from recylcerview
                            mAdapter.remove(position);
                            return;
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() { //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position + 1); //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount()); //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show(); //show alert dialog
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mCardListRecyclerView);
        updateUI();

        return view;
    }

    private void updateUI() {
        RecipeBook recipeBook = RecipeBook.get(getActivity());
        List<Recipe> mRecipeList = recipeBook.getRecipeBook();

        if (mAdapter == null) {
            mAdapter = new RecipeAdapter(mRecipeList);
            mCardListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecipeList(mRecipeList);
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

            mRecipeTextView = (TextView) itemView.findViewById(R.id.question_text_view);
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

        public RecipeAdapter(List<Recipe> cardList) {
            mRecipeList = cardList;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            Recipe card = mRecipeList.get(position);
            holder.bind(card);
        }

        public void remove(int position){
            RecipeBook.get(getActivity()).getmDatabase().execSQL("delete from " + "cards" + " where MID='" + (mRecipeList.get(position).getId()) + "'");
            mRecipeList.remove(position);
        }

        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }

        public void setRecipeList(List<Recipe> cards) {
            mRecipeList = cards;
        }
    }
}

