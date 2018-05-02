package com.jara.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {
    /** EntryAdapter class loading view with data of journal entries **/

    /* constructor */
    public EntryAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    /* Takes a view and fills the right elements with data from the cursor */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // get column index and set its content to view
        int colTitle = cursor.getColumnIndex("title");
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(cursor.getString(colTitle));

        int colDate = cursor.getColumnIndex("timestamp");
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(cursor.getString(colDate));

        int colContent = cursor.getColumnIndex("content");
        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(cursor.getString(colContent));

        int colMood = cursor.getColumnIndex("mood");
        String moodText = cursor.getString(colMood);
        ImageView mood = (ImageView) view.findViewById(R.id.mood);

        // set mood image according to mood
        if (moodText.equals("happy")) {
            mood.setImageResource(R.drawable.happy);
        }
        else if (moodText.equals("nice")) {
            mood.setImageResource(R.drawable.nice);
        }
        else if (moodText.equals("sad")) {
            mood.setImageResource(R.drawable.sad);
        }
        else if (moodText.equals("angry")) {
            mood.setImageResource(R.drawable.angry);
        }
        else {
            mood.setImageResource(R.drawable.okay);
        }
    }
}
