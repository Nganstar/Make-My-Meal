package com.company.android.sabr4730_ngan7260_final_project;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by akhma on 2017-12-02.
 */

public class RecipeFeedFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private static List<Recipe> mRecipeList = new ArrayList<>();
    public static String location ="";
    private static int check =0;

    String baseURL="https://www.finedininglovers.com/rss/recipes/latest";
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("check2", "onCreateView");
        RecipeBook.get(getActivity());
        View view = inflater.inflate(R.layout.recycler_view_recipe_list, container, false);

        if(checkNetwork()){
            StringBuilder sb = new StringBuilder();
            sb.append(baseURL);

            url = sb.toString();

                Log.d("check2", "Download"+check);

                new DownloadWebpageTask().execute();



        }
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        updateUI();
    }

    public boolean checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    } // isNetwork

    ///

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.i("HTTP: downloadUrl: ", "The response is: " + response);
            is = conn.getInputStream();
            return convertStreamToString(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    } //dowloadURL


    ///

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    } //convertString

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    ///////
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try { location = downloadUrl(url);
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    InputSource is = new InputSource(new StringReader(location));


                    Document doc = dBuilder.parse(is);

                    Element element=doc.getDocumentElement();

                    element.normalize();
                    NodeList nList = doc.getElementsByTagName("item");
                    Log.d("checklist","error: "+ nList);
                    for (int i=0; i<nList.getLength(); i++) {
                        Node node = nList.item(i);
                        Log.d("checknode","error: "+ node);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element currentElement = (Element) node;
                            String[] descritpionList=location.split("<description>");
                            String des= descritpionList[i+2].substring(0,descritpionList[i+2].indexOf("</description>"));
                            String image= des.substring(des.indexOf("<img align=\"left\" hspace=\"5\" src=\"")+34,des.indexOf("\" alt=\""));
                            String title= des.substring(des.indexOf(">")+1,des.indexOf("]]>"));

                            Recipe recipe= new Recipe();
                            recipe.setTitle(title);
                            recipe.setImage(image);
                            //recipe.setIngredients(ingredients);
                            //recipe.setSteps(directions);
                            recipe.setFavourite(false);
                            recipe.setURL(getValue("link",currentElement));
                            //recipe.setLink(getValue("link", currentElement));

                            Log.d("check", "runned "+recipe.getURL());
                            mRecipeList.add(recipe);
                        }
                    }



                } catch (Exception e) {e.printStackTrace();}


                return location;
            }

            catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            updateUI();
            //location = result;
        }
    } // DownloadWebpageTask
    ///

    @Override
    public void onResume() {
        RecipeBook.get(getActivity());
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mAdapter == null) {
            mAdapter = new RecipeAdapter(mRecipeList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecipeList(mRecipeList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Recipe mRecipe;

        private TextView mTitleTextView;
        private ImageView mImageView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recipe_list, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.description_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
        }

        public void bind(Recipe r) {
            mRecipe = r;
            mTitleTextView.setText(mRecipe.getTitle());

            if (mRecipe.getImage() != null){
                Picasso.with(getActivity().getApplicationContext())
                        .load(mRecipe.getImage())
                        .error(R.drawable.profile)
                        .into(mImageView);
            }
        }

        @Override
        public void onClick(View view) {
            ArrayList<String> list = new ArrayList<>();
            list.add(mRecipe.getImage());
            list.add(mRecipe.getTitle());
            list.add(mRecipe.getURL());

            Intent intent = WebDetailActivity.newIntent(getActivity(), list);
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
            Recipe recipe = mRecipeList.get(position);
            holder.bind(recipe);
        }


        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }

        public void setRecipeList(List<Recipe> recipeList) {
            mRecipeList = recipeList;
        }
    }
}
