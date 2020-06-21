package ru.notes.config

enum class Style() {
    ;

    companion object {
        const val USER_CSS = "user-view"
        const val USERS_CSS = "users-view"
        const val NOTE_CSS = "note-view"
        const val NOTES_CSS = "notes-view"
        const val BUTTON_CSS = "button-layout"
        const val FILTER_CSS = "filter"
        const val WRAPPER_CSS = "wrapper"
        const val EDITOR_CSS = "editor-layout"
        const val FIELD_WIDTH_CSS = "full-width"
        const val FIELD_PRIMARY_CSS = "primary"
        const val FIELD_ERROR_CSS = "error"
    }
}