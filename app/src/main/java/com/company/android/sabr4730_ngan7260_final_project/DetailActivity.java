package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by User on 12/3/2017.
 */

public class DetailActivity extends SingleFragmentActivity{
    private static final String EXTRA_RECIPE_ID =
            "Password";

    public static Intent newIntent(Context packageContext, String articleId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, articleId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        String cardId = (String) getIntent()
                .getSerializableExtra(EXTRA_RECIPE_ID);
        return RecipeDetailsFragment.newInstance(cardId);
    }
}
