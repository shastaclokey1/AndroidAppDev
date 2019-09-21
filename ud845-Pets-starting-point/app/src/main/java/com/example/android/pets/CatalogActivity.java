/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.dataContracts.PetContract;
import com.example.android.pets.dataContracts.PetDBHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity
{
    private PetDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new PetDBHelper(this);

        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId())
        {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertPet()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues dummyPetToto = new ContentValues();
        dummyPetToto.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        dummyPetToto.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        dummyPetToto.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        dummyPetToto.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, dummyPetToto);

        Log.v("insertPet()", Long.toString(newRowId));
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo()
    {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT
        };

        Cursor cursor = db.query(
                PetContract.PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("");
            cursor.moveToFirst();

            displayView.append(
                    cursor.getString(cursor.getColumnIndex(PetContract.PetEntry._ID)) + "\t" +
                    cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME)) + "\t" +
                    cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED)) + "\t" +
                    cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER)) + "\t" +
                    cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT))
            );

            while (cursor.moveToNext())
            {
                displayView.append("\n" +
                        cursor.getString(cursor.getColumnIndex(PetContract.PetEntry._ID)) + "\t" +
                        cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME)) + "\t" +
                        cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED)) + "\t" +
                        cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER)) + "\t" +
                        cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT))
                );
            }


        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
