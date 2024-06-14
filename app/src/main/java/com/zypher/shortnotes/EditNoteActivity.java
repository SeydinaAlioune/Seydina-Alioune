package com.zypher.shortnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText contentEditText;
    private Spinner categorySpinner;
    private Button saveButton;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        contentEditText = findViewById(R.id.contentEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("note_id", -1);
        String noteContent = intent.getStringExtra("note_content");
        String noteCategory = intent.getStringExtra("note_category");

        contentEditText.setText(noteContent);

        // Initialiser le Spinner avec les catégories
        setupCategorySpinner(noteCategory);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newContent = contentEditText.getText().toString();
                String newCategory = categorySpinner.getSelectedItem().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("note_id", noteId);
                resultIntent.putExtra("content", newContent);
                resultIntent.putExtra("category", newCategory);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void setupCategorySpinner(String noteCategory) {
        String[] categories = {"To Do", "In Progress", "Done", "Bug"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Sélectionner la catégorie actuelle de la note dans le Spinner
        if (noteCategory != null) {
            int position = adapter.getPosition(noteCategory);
            categorySpinner.setSelection(position);
        }
    }
}
