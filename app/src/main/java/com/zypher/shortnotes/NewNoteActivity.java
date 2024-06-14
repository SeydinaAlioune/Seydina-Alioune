
package com.zypher.shortnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        final EditText newTitleEditText = findViewById(R.id.titleEditText);
        final EditText newContentEditText = findViewById(R.id.contentEditText);
        Button saveNewNoteButton = findViewById(R.id.saveButton);


        saveNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newTitleEditText.getText().toString();
                String content = newContentEditText.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("note_title", title);
                    resultIntent.putExtra("note_content", content);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}
