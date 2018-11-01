package com.example.android.dbjavatutorial;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    //needs both these member variable as speaks to both db and WordViewModel (? does it speak to WordViewModel?)
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();//is this not just assigning itself to itself?
        //or do we use the getAllWords method because of the LiveData wrapper,
        //so list of words is always up to date because of observer and Room threading?
    }

    //LiveData wrapper so list always up to date
    LiveData<List<Word>> getAllWords(){
        return allWords;
    }

    //wrapper for insert method from DAO, so it is called on different thread to UI thread.
    public void insert(Word word){
        new insertAsyncTask(wordDao).execute(word);
    }

    //learn how AsyncTask works
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        public insertAsyncTask(WordDao wordDao) {
            asyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
