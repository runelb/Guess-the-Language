package com.example.guessthelanguage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.guessthelanguage.DbContract.LanguageTable;

import java.util.ArrayList;
import java.util.Random;

public class LanguageQuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GuesstheLanguage.db";
    private static final int DATABASE_VERSION = 1;
    private static final int numOfLangs = 5;    // actually change to be set by the number created?

    private SQLiteDatabase db;

    public LanguageQuizDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // change difficulty to integer!!
        final String SQL_CREATE_LANGUAGE_TABLE = "CREATE TABLE " +
                LanguageTable.TABLE_NAME + " ( " +
                LanguageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LanguageTable.COLUMN_LANGUAGE + " TEXT, " +
                LanguageTable.COLUMN_DIFFICULTY + " TEXT, " +
                LanguageTable.COLUMN_FAMILY + " TEXT, " +
                LanguageTable.COLUMN_REGION + " TEXT, " +
                LanguageTable.COLUMN_PHRASE1 + " TEXT, " +
                LanguageTable.COLUMN_PHRASE2 + " TEXT, " +
                LanguageTable.COLUMN_PHRASE3 + " TEXT, " +
                LanguageTable.COLUMN_PHRASE4 + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_LANGUAGE_TABLE);
        fillLanguageTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LanguageTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillLanguageTable() {
        Language l1 = new Language("Swedish", "medium", "ger", "eur", "Hej, hur mår du?", "p2", "p3", "p4");
        addLanguage(l1);
        Language l2 = new Language("English", "easy", "ger", "eur", "Hi, how are you?", "p2", "p3", "p4");
        addLanguage(l2);
        Language l3 = new Language("Spanish", "easy", "lat", "eur", "Hola ¿cómo estás?", "p2", "p3", "p4");
        addLanguage(l3);
        Language l4 = new Language("French", "easy", "lat", "eur", "Salut, ça va?", "p2", "p3", "p4");
        addLanguage(l4);
        Language l5 = new Language("Turkish", "medium", "tur", "mid", "Merhaba nasılsın?", "p2", "p3", "p4");
        addLanguage(l5);
    }
    // above and below, Language class object is used to just fill the database, could be more efficient without using objects
    private void addLanguage(Language language) {
        ContentValues cv = new ContentValues();
        cv.put(LanguageTable.COLUMN_LANGUAGE, language.getLanguage());
        cv.put(LanguageTable.COLUMN_DIFFICULTY, language.getDifficulty());
        cv.put(LanguageTable.COLUMN_FAMILY, language.getFamily());
        cv.put(LanguageTable.COLUMN_REGION, language.getRegion());
        cv.put(LanguageTable.COLUMN_PHRASE1, language.getPhrase1());
        cv.put(LanguageTable.COLUMN_PHRASE2, language.getPhrase2());
        cv.put(LanguageTable.COLUMN_PHRASE3, language.getPhrase2());
        cv.put(LanguageTable.COLUMN_PHRASE4, language.getPhrase4());
        db.insert(LanguageTable.TABLE_NAME, null, cv);
    }



    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<Question>();
        db = getReadableDatabase();

        // selection_args for categories and difficulty etc.
        Cursor c = db.rawQuery("SELECT * FROM " + LanguageTable.TABLE_NAME, null);

        // cursor must jump around for random languages
        // c.moveToPosition([some number, 0 to end])
        // use new java.lang.Math.random()
        // ranGen = new java.util.Random()
        // ranGen.nextInt(int upperBound)

        // new control of how many questions to make... set limit = 10 to start?

        // difficulty must be selected before/at this point!



        // randomly select a phrase     for now just the one

        // randomly select options      MAYBE returning a list of random options and shuffling that?

        Random randGen = new java.util.Random();
        String rightAnswer;
        int answerNr;

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(LanguageTable.COLUMN_PHRASE1)));
                rightAnswer = c.getString(c.getColumnIndex(LanguageTable.COLUMN_LANGUAGE));
                int currentPosition = c.getPosition();

                // currently will repeat options

                ArrayList<Integer> positions = new ArrayList<>();
                positions.add(currentPosition);
                while (positions.size() < numOfLangs) {           // overwrites one rand gen
                    int newPosition = randGen.nextInt(numOfLangs);
                    if (!positions.contains(newPosition)){
                        positions.add(newPosition);
                    }
                }   // now list of unique positions

                c.moveToPosition(randGen.nextInt(numOfLangs));
                question.setOption1(c.getString(c.getColumnIndex(LanguageTable.COLUMN_LANGUAGE)));
                c.moveToPosition(randGen.nextInt(numOfLangs));
                question.setOption2(c.getString(c.getColumnIndex(LanguageTable.COLUMN_LANGUAGE)));
                c.moveToPosition(randGen.nextInt(numOfLangs));
                question.setOption3(c.getString(c.getColumnIndex(LanguageTable.COLUMN_LANGUAGE)));
                c.moveToPosition(randGen.nextInt(numOfLangs));
                question.setOption4(c.getString(c.getColumnIndex(LanguageTable.COLUMN_LANGUAGE)));

                // overwrite one option with the correct answer
                answerNr = randGen.nextInt(4);
                question.setAnswerNr(answerNr);
                if (answerNr == 0){
                    question.setOption1(rightAnswer);
                } else if (answerNr == 1) {
                    question.setOption2(rightAnswer);
                } else if (answerNr == 2) {
                    question.setOption3(rightAnswer);
                } else if (answerNr == 3) {
                    question.setOption4(rightAnswer);
                }
                questionList.add(question);
                c.moveToPosition(currentPosition);  // move back before continuing through
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    /*
    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<Question>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[] { difficulty };
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }*/
}
