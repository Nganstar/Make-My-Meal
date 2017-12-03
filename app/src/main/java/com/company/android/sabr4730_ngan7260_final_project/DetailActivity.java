package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by User on 12/3/2017.
 */

public class DetailActivity extends SingleFragmentActivity{
    private static final String EXTRA_ARTICLE_ID =
            "example.com.sabr4730_a5";

    public static Intent newIntent(Context packageContext, String articleId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        String cardId = (String) getIntent()
                .getSerializableExtra(EXTRA_ARTICLE_ID);
        return RecipeDetailsFragment.newInstance(cardId);
    }
}
