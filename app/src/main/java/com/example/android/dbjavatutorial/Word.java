package com.example.android.dbjavatutorial;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {

    //need getter and constructor in any entity class so Room knows to instantiate the object
    //(into SQLite entities)

    @PrimaryKey
    @NonNull
    private String word;

    public Word(@NonNull String word){
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }
}
