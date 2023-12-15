class NoteNotFoundException(message: String) : RuntimeException(message)

data class Note(
    var noteId: Int,
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    var deleted: Boolean = false

)

data class NoteСomment(
    val noteId: Int,
    val commentId: Int,
    val ownerId: Int,
    val message: String,
    var deleted: Boolean = false
)

object NotesService {
    var notes = emptyArray<Note>()
    var comments = emptyArray<NoteСomment>()
    var deletedComments = emptyArray<NoteСomment>()

    fun clear () {
        notes = emptyArray()
        comments = emptyArray()
        deletedComments = emptyArray()
    }

    fun addNote(note: Note): Note {
        notes += note.copy(noteId = note.noteId)
        return notes.last()
    }

    fun editNote(noteId: Int, note: Note): Note {
        for ((index, n) in notes.withIndex())
            if (n.noteId == noteId && !note.deleted) {
                notes[index] = n.copy(
                    title = note.title,
                    text = note.text,
                    privacy = note.privacy,
                    commentPrivacy = note.commentPrivacy
                )
                return notes[index]
            }
        throw NoteNotFoundException("Not found Note ID$noteId")
    }

    fun getNotes() {
        for ((index, n) in notes.withIndex()) {
            println("Notes list: " + n.noteId + " - " + n.title + " - " + n.text)
        }
        println()
    }

    fun removeNote(noteId: Int): Note {
        for ((index, n) in notes.withIndex()) {
            if (n.noteId == noteId && !n.deleted) {
                notes[index] = n.copy(deleted = true)
                notes = notes.filter { it.deleted == n.deleted }.toTypedArray()
            }
        }
        return notes.last()
    }

    fun createComment(noteId: Int, noteСomment: NoteСomment): NoteСomment {
        for ((index, note) in notes.withIndex()) {
            if (note.noteId == noteId && !note.deleted) {
                comments += noteСomment.copy(
                    commentId = noteСomment.commentId, ownerId = noteСomment.ownerId,
                    message = noteСomment.message
                )
            }
        }
        return comments.last()
    }

    fun editComment(commentId: Int, comment: NoteСomment): NoteСomment {
        for ((index, com) in comments.withIndex()) {
            if (com.commentId == commentId && !com.deleted) {
                comments[index] = comment.copy(message = comment.message)
            }
            return comments[index]
        }
        throw NoteNotFoundException("Not found Note ID $commentId")
    }

    fun removeComment(noteId: Int, commentId: Int): NoteСomment {
        for ((index, commen) in comments.withIndex()) {
            if (commen.noteId == noteId && commen.commentId == commentId && !commen.deleted ) {
                comments[index] = commen.copy(deleted = true)
                deletedComments+= comments[index]
                comments = comments.filter { it.deleted == commen.deleted }.toTypedArray()
            }
        }
        return comments.last()
    }

    fun getComment(noteId: Int) {
        for ((index, commen) in comments.withIndex()) {
            if (commen.noteId == noteId  && !commen.deleted) {
                println("Comments for noteID $noteId -  " + commen.commentId + " * " + commen.ownerId + " * " + commen.message)
            }
        }
    }

    fun restoreComment(noteId: Int, commentId: Int): NoteСomment {
        for ((index, commR) in deletedComments.withIndex()) {
            if (commR.noteId == noteId && commR.commentId == commentId && commR.deleted) {
                deletedComments[index] = commR.copy(deleted = false)
                comments += deletedComments[index]
            }
        }
        return comments.last()
    }
}