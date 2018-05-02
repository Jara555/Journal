package com.jara.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    /** DetailActivity class to set up details of individual journal entries **/

    /* Loads detailed journal entry layout */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get bundle out of intent
        Intent intent = getIntent();
        Bundle bundle = (Bundle) intent.getBundleExtra("bundle");

        // set all info of bundle to text views
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(bundle.getString("title"));

        TextView timestamp = (TextView) findViewById(R.id.timestamp);
        timestamp.setText(bundle.getString("timestamp"));

        TextView mood = (TextView) findViewById(R.id.mood);
        mood.setText(bundle.getString("mood"));

        TextView content = (TextView) findViewById(R.id.content);
        content.setText(bundle.getString("content"));
    }
}
