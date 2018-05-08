package com.example.guessthelanguage;

import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";    // change difficulty ratings to ints

    private String language;
    private String difficulty;
    private String family;
    private String region;
    private String phrase1;
    private String phrase2;
    private String phrase3;
    private String phrase4;

    public Language() {
    }

    public Language(String language, String difficulty, String family, String region,
                    String phrase1, String phrase2, String phrase3, String phrase4) {
        this.language = language;
        this.difficulty = difficulty;
        this.family = family;
        this.region = region;
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
        this.phrase3 = phrase3;
        this.phrase4 = phrase4;
    }

    protected Language(Parcel in) {
        super();
        language = in.readString();
        difficulty = in.readString();
        family = in.readString();
        region = in.readString();
        phrase1 = in.readString();
        phrase2 = in.readString();
        phrase3 = in.readString();
        phrase4 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(language);
        dest.writeString(difficulty);
        dest.writeString(family);
        dest.writeString(region);
        dest.writeString(phrase1);
        dest.writeString(phrase2);
        dest.writeString(phrase3);
        dest.writeString(phrase4);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhrase1() {
        return phrase1;
    }

    public void setPhrase1(String phrase1) {
        this.phrase1 = phrase1;
    }

    public String getPhrase2() {
        return phrase2;
    }

    public void setPhrase2(String phrase2) {
        this.phrase2 = phrase2;
    }

    public String getPhrase3() {
        return phrase3;
    }

    public void setPhrase3(String phrase3) {
        this.phrase3 = phrase3;
    }

    public String getPhrase4() {
        return phrase4;
    }

    public void setPhrase4(String phrase4) {
        this.phrase4 = phrase4;
    }

    public static String[] getAllDifficultyLevels() {
        return new String[]{
                DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD
        };
    }
}
