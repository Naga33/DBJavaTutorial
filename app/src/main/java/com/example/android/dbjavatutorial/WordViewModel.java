package com.example.android.dbjavatutorial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;//require this member variable because class talks to repository.
    private LiveData<List<Word>> allWords;//to cache list of words

    //never pass context to ViewModel WHY?
    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
        allWords = repository.getAllWords();
    }

    //wrapper on getAllWords() and insert() from repository to completely hide methods from UI
    LiveData<List<Word>> getAllWords(){
        return allWords;
    }

    public void insert(Word word){
        repository.insert(word);
    }
}
