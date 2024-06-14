package com.zypher.shortnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText contentEditText;
    private Spinner categorySpinner;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        contentEditText = findViewById(R.id.contentEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent.hasExtra("content")) {
            contentEditText.setText(intent.getStringExtra("content"));
        }
        if (intent.hasExtra("category")) {
            String category = intent.getStringExtra("category");
            int spinnerPosition = adapter.getPosition(category);
            categorySpinner.setSelection(spinnerPosition);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString().trim();
                String category = categorySpinner.getSelectedItem().toString();

                if (content.isEmpty()) {
                    // Afficher un message d'erreur si le contenu est vide
                    contentEditText.setError("Veuillez entrer le contenu de la note");
                    contentEditText.requestFocus();
                    return;
                }

                // Retourner les données à MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("content", content);
                resultIntent.putExtra("category", category);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
