package com.jara.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
        mood = "okay";
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

    /* Sets mood based on clicked button image */
    public void setMood(View view) {
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
                mood = "okay";
                break;
        }
    }
}
