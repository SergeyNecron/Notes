package ru.notes.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.notes.component.NoteEditor;
import ru.notes.model.Note;
import ru.notes.repository.NoteRepository;

@Route("")
public class MainView extends VerticalLayout {
    private final NoteRepository noteRepository;

    private final NoteEditor noteEditor;

    private final TextField filter = new TextField();
    private final Button addNewNoteButton = new Button("New note", VaadinIcon.PLUS.create());
    // build layout
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewNoteButton);
    private Grid<Note> grid = new Grid<>(Note.class);

    @Autowired
    public MainView(NoteRepository noteRepository, NoteEditor noteEditor) {
        this.noteRepository = noteRepository;
        this.noteEditor = noteEditor;

        filter.setPlaceholder("filter");
        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> notesList(field.getValue()));

        add(toolbar, grid, noteEditor);
        // Connect selected Note to editor or hide if none is selected
        grid
                .asSingleSelect()
                .addValueChangeListener(e -> noteEditor.editNote(e.getValue()));
        // Instantiate and edit new Note the new button is clicked
        addNewNoteButton.addClickListener(e -> noteEditor.editNote(new Note()));
        // Listen changes made by the editor, refresh data from backend
        noteEditor.setChangeHandler(() -> {
            noteEditor.setVisible(false);
            notesList(filter.getValue());
        });
        // Initialize listing
        notesList("");
    }

    private void notesList(String name) {
        if (name.isEmpty()) {
            grid.setItems(this.noteRepository.findAll());
        } else {
            grid.setItems(this.noteRepository.findByName(name));
        }
    }
//    private void notesList(String name) {
//        if (name.isEmpty()) {
//            grid.setItems(this.noteService.getAllNotes());
//        } else {
//            grid.setItems(this.noteService.findNotes(name));
//        }
//    }
}
