package com.jara.journal;

import java.io.Serializable;

public class JournalEntry implements Serializable {
    /** JournalEntry class containing all properties and methods for JournalEntry objects **/

    // properties
    private static int id;
    private String title;
    private String content;
    private String mood;
    private static String timestamp;

    /* Constructor */
    public JournalEntry(String title, String content, String mood) {
        this.title = title;
        this.content = content;
        this.mood = mood;
    }

    /* Getters */
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getMood() {
        return mood;
    }

    public String getTimestamp() {
        return timestamp;
    }

    /* Setters */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
