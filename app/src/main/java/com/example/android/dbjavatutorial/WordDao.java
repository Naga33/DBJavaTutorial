package com.example.android.dbjavatutorial;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    // @Insert(onConflict = OnConflictStrategy.REPLACE) use this if I have > 1 entity ('column') in database.
    @Insert
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    //ordering is not necessary but makes testing straightforward.
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
    //As Room does not allow db queries on main thread, LiveData can run query asynchronously on background thread
    //originally the above line was just <List<Word>> but the LiveData class from the lifecycle library
    //is wrapped around the list of words so that an observer can be set up in MainActivity.
    //Any changes that occur here updates the WordRepository which updates the WordViewModel
    //which notifies the MainActivity (notify function built into LiveData) which observes the WordViewModel,
    //so that the MainActivity can call onChanged() method which causes
    //adapter to call setWords() method,
    //which updates its cached data and refreshes the the RecyclerView

}