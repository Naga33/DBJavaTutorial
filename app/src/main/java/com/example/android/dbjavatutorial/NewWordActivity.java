package com.example.android.dbjavatutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    //why do we need this, how does it work (way to talk to Room db?)
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText editWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);//activity_new_word is an xml layout file
        editWordView = findViewById(R.id.edit_word);//text edit in the xml file

        final Button button = findViewById(R.id.button_save);//why is this declared here not above with editWordView?

        //this is where we code what happens to listeners (in the activities)
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = editWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();//learn how finish and setResult etc work
            }
        });
    }

}

