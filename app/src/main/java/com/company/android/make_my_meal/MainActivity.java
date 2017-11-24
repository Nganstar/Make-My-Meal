package com.company.android.make_my_meal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);  //for fragment
        setContentView(R.layout.activity_main);
    }
////////////////////////////////////////////////////////////////////////////////////////////////
//displaying menu, need modification when fragment is implemented

    @Override
public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
}
    @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.new_card: // Create the second activity to add a new card
                intent = new Intent(this,AddRecipe.class);
                startActivity(intent);
            case R.id.scan_doc: // Create the second activity to add a new card
                intent = new Intent(this,ScanDoc.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    } //onOptionsItemsSelected

    ///////////////////////////////////////////////////////////////////////////////////////////////
}
