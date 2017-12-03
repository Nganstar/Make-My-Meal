package com.company.android.sabr4730_ngan7260_final_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by akhma on 2017-12-02.
 */

public class AllRecipesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.all_recipe_fragment, container, false);
        return rootView;
    }
}
