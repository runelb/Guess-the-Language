package com.example.guessthelanguage;


import android.provider.BaseColumns;

public class DbContract {

    private DbContract() {
    }

    // for setting up the database
    public static class LanguageTable implements BaseColumns {
        public static final String TABLE_NAME = "languages";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_FAMILY = "family";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_PHRASE1 = "phrase1";
        public static final String COLUMN_PHRASE2 = "phrase2";
        public static final String COLUMN_PHRASE3 = "phrase3";
        public static final String COLUMN_PHRASE4 = "phrase4";
    }
}
