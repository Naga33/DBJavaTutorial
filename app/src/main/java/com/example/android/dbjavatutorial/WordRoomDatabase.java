package com.example.android.dbjavatutorial;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

//entities are the 'tables' in the db, what is version?
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    //define DAO(s) that work with db ('getter' method). Don't understand this syntax.
    //abstract constructor for a different class or something?
    public abstract WordDao wordDao();


    //volatile because of threading, object read directly from memory and not from stack reference
    //(prevents current thread having a different value for the object than the value in heap memory)
    private static volatile WordRoomDatabase INSTANCE;


    //only need one db for whole app, so singleton used
    //why static? so can be called anywhere not just in current thread? (i think this is the case)
    static WordRoomDatabase getDatabase(final Context context){//what is context?
        if(INSTANCE == null){
            synchronized (WordRoomDatabase.class){//why do we refer to own class here?
                if(INSTANCE == null){

                    //databaseBuilder() does the following:
                    //creates RoomDatabase object from WordRoomDatabase class,
                    //creates the object in application context (what is this?)
                    //names it "word_database"

                    //Callback deletes all app content and repopulates db on app restart

                    //build() executes db build sequence

                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            WordRoomDatabase.class,
                            "word_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }


    //Callback deletes all app content and repopulates db on app restart
    //as Room db operations cannot be conducted in UI thread,
    //onOpen executes AsyncTask to add content to db (while repopulating)
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        //deletes all current content of db and repopulates
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }

}
