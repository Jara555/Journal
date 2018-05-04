/****************************************************************************
 * MainActivity.java
 *
 * appstudio mprog
 * Jara Linders
 * 04-05-2018
 *
 * This program implements the Journal app for android phones. It uses the
 * JournalEntry class to create objects which are visualized in a list by the
 * EntryAdapter class. The EntryDatabase class manages the SQLite database and
 * all data within. New journal entries can be added by clicking on the floating
 * action button which redirects to the InputActivity. Furthermore detailed entries
 * are visualized by the DetailActivity after clicking on them.
 ***************************************************************************/

package com.jara.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainActivity extends AppCompatActivity {
    /** MainActivity class implementing the Journal app **/

    // declare class variables
    private EntryDatabase db;
    private EntryAdapter adapter;

    /* Creates main activity layout */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create singleton instance of database
        db = EntryDatabase.getInstance(getApplicationContext());

        // store all data in cursor
        Cursor cursor = db.selectAll();

        // initialize adapter on all data and set to list view
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new EntryAdapter(this, R.layout.entry_row, cursor, FLAG_ACTIVITY_SINGLE_TOP);
        listView.setAdapter(adapter);

        // create click and long click listener for items in list view
        OnItemClickListener listClick = new OnItemClickListener();
        listView.setOnItemClickListener(listClick);
        listView.setOnItemLongClickListener(listClick);
    }

    /* Directs to input activity when floating action button is pressed */
    public void addInput(View view) {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        /** Class responding to clicks on items of list view **/

        /* Gets called for a short click: Redirects to detailed journal entry */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // get cursor of clicked entry
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            // create bundle to store cursor info in
            Bundle bundle = new Bundle();

            // store info of all columns in bundle
            String[] titles = new String[]{"title", "timestamp", "content", "mood"};
            for (String title : titles) {
                int colIndex = cursor.getColumnIndex(title);
                bundle.putString(title, cursor.getString(colIndex));
            }

            // create intent and pass bundle through to detail activity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }

        /* Gets called for a long click: Deletes journal entry */
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // get confirmation alert
            AlertDialog diaBox = AskOption(parent, position);
            diaBox.show();

            return true;
        }
    }

    /* Updates view */
    private void updateData() {
        Cursor cursor = db.selectAll();
        adapter.swapCursor(cursor);
    }

    /* Asks confirmation before deleting an item (Source: https://stackoverflow.com/a/11740348) */
    private AlertDialog AskOption(final AdapterView<?> parent, final int position) {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setIcon(R.drawable.angry)

                // delete
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // get cursor of clicked item
                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                        // get column index and id at that location
                        int colId = cursor.getColumnIndex("_id");
                        long clickedId = cursor.getInt(colId);

                        // for retrieved id call delete on database and update view
                        db.deleteEntry(clickedId);
                        updateData();

                        dialog.dismiss();
                    }
                })

                // not delete
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}

