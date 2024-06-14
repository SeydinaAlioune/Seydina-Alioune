package com.zypher.shortnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner filterSpinner;
    private LinearLayout notesContainer;
    private Button addNoteButton;

    private List<Note> notes = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterSpinner = findViewById(R.id.filterSpinner);
        notesContainer = findViewById(R.id.notesContainer);
        addNoteButton = findViewById(R.id.addNoteButton);

        // Initialisation du Spinner avec les catégories disponibles
        setupSpinner();

        // Gestion du clic sur le bouton pour ajouter une nouvelle note
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancer l'activité pour ajouter une nouvelle note
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                addNoteActivityResultLauncher.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> addNoteActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String content = result.getData().getStringExtra("content");
                    String category = result.getData().getStringExtra("category");

                    // Ajouter la note à la liste
                    Note note = new Note("", content, category); // Titre vide
                    notes.add(note);

                    // Afficher les notes selon le filtre actuel
                    filterNotes((String) filterSpinner.getSelectedItem());
                }
            });

    private final ActivityResultLauncher<Intent> editNoteActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int noteId = result.getData().getIntExtra("note_id", -1);
                    String content = result.getData().getStringExtra("content");
                    String category = result.getData().getStringExtra("category");

                    // Mettre à jour la note dans la liste
                    updateNoteInList(noteId, content, category);

                    // Réafficher les notes selon le filtre actuel
                    filterNotes((String) filterSpinner.getSelectedItem());
                }
            });

    private void setupSpinner() {
        // Exemple de catégories disponibles
        String[] categories = {"All", "To Do", "In Progress", "Done", "Bug"};

        // Adapter pour le Spinner
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        // Écouteur de sélection du Spinner
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Filtrer les notes en fonction de la catégorie sélectionnée
                String selectedCategory = (String) parent.getItemAtPosition(position);
                filterNotes(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Par défaut, ne rien faire
            }
        });
    }

    private void filterNotes(String category) {
        // Effacer les notes actuellement affichées
        notesContainer.removeAllViews();

        // Afficher seulement les notes de la catégorie sélectionnée
        for (Note note : notes) {
            if (category.equals("All") || note.getCategory().equals(category)) {
                addNoteView(note);
            }
        }
    }

    private void addNoteView(Note note) {
        // Ajouter la vue de la note à notesContainer
        View noteView = getLayoutInflater().inflate(R.layout.note_item, notesContainer, false);

        TextView contentTextView = noteView.findViewById(R.id.contentTextView);
        TextView categoryTextView = noteView.findViewById(R.id.categoryTextView);
        Button editButton = noteView.findViewById(R.id.editButton);
        View categoryColorView = noteView.findViewById(R.id.categoryColorView);

        // Afficher le contenu de la note directement comme texte principal
        contentTextView.setText(note.getContent());
        categoryTextView.setText(note.getCategory());

        // Couleur de la catégorie
        switch (note.getCategory()) {
            case "To Do":
                categoryColorView.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "In Progress":
                categoryColorView.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "Done":
                categoryColorView.setBackgroundColor(getResources().getColor(R.color.brown));
                break;
            case "Bug":
                categoryColorView.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            default:
                categoryColorView.setBackgroundColor(getResources().getColor(R.color.gray));
                break;
        }

        // Gestion du clic sur le bouton pour modifier la note
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancer l'activité pour modifier la note
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra("note_id", notes.indexOf(note));
                intent.putExtra("note_content", note.getContent());
                intent.putExtra("note_category", note.getCategory());
                editNoteActivityResultLauncher.launch(intent);
            }
        });

        // Ajouter la vue de la note au conteneur
        notesContainer.addView(noteView);
    }

    private void updateNoteInList(int noteId, String newContent, String newCategory) {
        // Vérifier si l'ID de la note est valide
        if (noteId >= 0 && noteId < notes.size()) {
            // Mettre à jour le contenu et la catégorie de la note
            Note note = notes.get(noteId);
            note.setContent(newContent);
            note.setCategory(newCategory);
        }
    }
}
