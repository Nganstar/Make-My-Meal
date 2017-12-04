package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by User on 12/4/2017.
 */

public class WebDetailActivity extends SingleFragmentActivity{
    private static final String EXTRA_ARTICLE_ID =
            "example.com.sabr4730_a4";

    @Override
    protected Fragment createFragment() {
        ArrayList<String> list =  getIntent()
                .getStringArrayListExtra(EXTRA_ARTICLE_ID);
        return ArticleDetailFragment.newInstance(list);
    }

    public static Intent newIntent(Context packageContext, ArrayList<String> list) {
        Intent intent = new Intent(packageContext, WebDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, list);
        return intent;
    }
}
