package ru.notes.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.notes.model.Note;
import ru.notes.repository.NoteRepository;


@SpringComponent
@UIScope
public class NoteEditor extends VerticalLayout implements KeyNotifier {
    private final NoteRepository noteRepository;

    private Note note;

    private TextField title = new TextField("", "titleNote");
    private TextField tag = new TextField();
    private TextField description = new TextField("", "description");


    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

    private Binder<Note> binder = new Binder<>(Note.class);

    private ChangeHandler changeHandler;

    @Autowired
    public NoteEditor(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;

        add(title, tag, description, buttons);

        binder.bindInstanceFields(this);
        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
//        setVisible(false);
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    private void save() {
        noteRepository.save(note);
        changeHandler.onChange();
    }

    private void delete() {
        noteRepository.delete(note);
        changeHandler.onChange();
    }

    public void editNote(Note note) {

        if (note == null) {
            setVisible(false);
            return;
        }
        this.note = noteRepository.findById(note.getId()).orElse(note);

        binder.setBean(this.note);

        setVisible(true);
        title.focus();
    }

    public interface ChangeHandler {
        void onChange();
    }
}
