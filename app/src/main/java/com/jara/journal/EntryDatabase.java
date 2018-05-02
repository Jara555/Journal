package com.jara.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class EntryDatabase extends SQLiteOpenHelper {
    /** Database helper class to store journal entries in SQLite Database **/

    // set constant variables
    private static final String DB_NAME = "entries";
    private static final int DB_VERSION = 1;

    // storage of singleton
    private static EntryDatabase instance = null;

    /* Constructor */
    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* Returns instance if available */
    public static EntryDatabase getInstance(Context context) {
        // if instance is not available call constructor
        if (instance == null) {
            instance = new EntryDatabase(context, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    /* Generates table */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute SQL query for creating entries table
        db.execSQL("create table entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, " +
                "content TEXT, mood TEXT, timestamp DATETIME DEFAULT (datetime('now','localtime')));");

        // insert sample items
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES ('Happy day', 'This was a " +
                "perfect day! I went to the beach and ate an icecream.', 'great');");
        db.execSQL("INSERT INTO entries (title, content, mood) VALUES ('Sad day', 'This was a sad " +
                "day. My hamster died. I burried him in the garden.', 'sad');");
    }

    /* Drops and recreates table */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS entries;");
        onCreate(db);
    }

    /* Selects all of entries database */
    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM entries;", null);
    }

    /* Inserts data into entries database */
    public void insertEntry(JournalEntry journalEntry) {
        SQLiteDatabase db = getWritableDatabase();

        // set info of journalEntry object to content values
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", (String) journalEntry.getTitle());
        contentValues.put("content", (String) journalEntry.getContent());
        contentValues.put("mood", (String) journalEntry.getMood());

        // insert content values into entries table
        db.insert("entries", null, contentValues);
    }

    /* Delete data of database */
    public void deleteEntry(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("entries", "_id=" + id, null);
    }
}
