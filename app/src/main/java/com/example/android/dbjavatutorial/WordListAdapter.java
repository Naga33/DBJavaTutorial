package com.example.android.dbjavatutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    //Need this class for the RecyclerView.


    private final LayoutInflater inflater;
    private List<Word> words; //caches copy of words

    WordListAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    //this method is invoked when observer causes onChanged() method to be called (in MainActivity)
    //this method updates cached data and refreshes the displayed list
    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (words != null) {
            Word current = words.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else return 0;
    }


    class WordViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemView;

        private WordViewHolder(View itemView){
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
