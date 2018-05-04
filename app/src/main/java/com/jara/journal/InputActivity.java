package com.jara.journal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class InputActivity extends AppCompatActivity {
    /** InputActivity class to add a journal entry to the app **/

    // create class variable mood
    private String mood;

    /* Loads the input activity layout */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // set mood default to neutral
        mood = "default";
    }

    /* Submits new journal entry to database */
    public void addEntry(View view) {
        // connect to database
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

        // retrieve title and content of edit text
        EditText editTitle = (EditText) findViewById(R.id.editTitle);
        String title = (String) editTitle.getText().toString();

        EditText editContent = (EditText) findViewById(R.id.editContent);
        String content = (String) editContent.getText().toString();

        // create new journal entry and insert in database
        JournalEntry journalEntry = new JournalEntry(title, content, mood);
        db.insertEntry(journalEntry);

        // go back to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /* Saves information in bundle */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("mood", mood);
    }

    /* Restores information out of bundle */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        mood = inState.getString("mood");

        // restore selection based on mood
        if (mood.equals("happy")) {
            ImageButton image = (ImageButton) findViewById(R.id.happy);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.selected));
        }
        else if (mood.equals("nice")) {
            ImageButton image = (ImageButton) findViewById(R.id.nice);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.selected));
        }
        else if (mood.equals("okay")) {
            ImageButton image = (ImageButton) findViewById(R.id.okay);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.selected));
        }
        else if (mood.equals("sad")) {
            ImageButton image = (ImageButton) findViewById(R.id.nice);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.selected));
        }
        else if (mood.equals("angry")) {
            ImageButton image = (ImageButton) findViewById(R.id.nice);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.selected));
        }
    }

    /* Sets mood based on clicked button image */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMood(View view) {
        // unselect all other mood buttons
        int[] moodIds = new int[]{R.id.happy, R.id.nice, R.id.okay, R.id.sad, R.id.angry};
        for (int id : moodIds) {
            ImageButton image = (ImageButton) findViewById(id);
            image.setBackgroundTintList(getResources().getColorStateList(R.color.unselected));
        }

        // select the clicked image button
        view.setBackgroundTintList(getResources().getColorStateList(R.color.selected));

        // get id of selected mood button and store in mood variable
        int id = view.getId();
        switch (id) {
            case R.id.happy:
                mood = "happy";
                break;
            case R.id.nice:
                mood = "nice";
                break;
            case R.id.okay:
                mood = "okay";
                break;
            case R.id.sad:
                mood = "sad";
                break;
            case R.id.angry:
                mood = "angry";
                break;
            default:
                mood = "default";
                break;
        }
    }
}
