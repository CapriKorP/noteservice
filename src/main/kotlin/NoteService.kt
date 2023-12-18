import kotlin.collections.mutableListOf as mutableListOf

class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)

data class Note(
    var noteId: Int,
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    var isDeleted: Boolean = false,
    val ownerId: Int,
    var comments: MutableList<NoteСomment> = mutableListOf()
)

data class NoteСomment(
    val comId: Int,
    var commentId: Int,
    val ownerId: Int,
    val message: String,
    var isDeleted: Boolean = false
)

object NotesService {
    var notes = mutableListOf<Note>()
    var comments = mutableListOf<NoteСomment>()
    var count = 0

    fun clear() {
        notes = mutableListOf()
        comments = mutableListOf()
    }

    fun addNote(note: Note): Note {
        notes += note.copy(noteId = note.noteId)
        return notes.last()
    }

    fun editNote(noteId: Int, note: Note): Note {
        for ((index, n) in notes.withIndex())
            if (n.noteId == noteId && !note.isDeleted) {
                notes[index] = n.copy(
                    title = note.title,
                    text = note.text,
                    privacy = note.privacy,
                    commentPrivacy = note.commentPrivacy,
                    isDeleted = note.isDeleted
                )
                return notes[index]
            }
        throw NoteNotFoundException("Not found Note ID$noteId")
    }

    fun printNotes() {
        for ((index, n) in notes.withIndex()) {
            println("Notes list: " + n.noteId + " - " + n.title + " - " + n.text)
        }
        println()
    }

    fun removeNote(noteId: Int): Boolean {
        for ((index, n) in notes.withIndex()) {
            if (n.noteId == noteId && !n.isDeleted) {
                notes[index] = n.copy(isDeleted = true)
                notes = notes.filter { !it.isDeleted }.toMutableList()
                return true
            }
        }
        throw NoteNotFoundException("Not found Note ID $noteId")
    }

    fun createComment(noteId: Int, noteСomment: NoteСomment): NoteСomment {
        for ((index, note) in notes.withIndex()) {
            if (note.noteId == noteId && !note.isDeleted) {
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
            if (com.commentId == commentId && !com.isDeleted) {
                comments[index] = comment.copy(message = comment.message)
            }
            return comments[index]
        }
        throw CommentNotFoundException("Not found Comment ID $commentId")
    }

    fun removeComment(noteId: Int, commentId: Int): Boolean {
        for ((index, commen) in comments.withIndex()) {
            if (commen.comId == noteId && commen.commentId == commentId && !commen.isDeleted) {
                comments[index] = commen.copy(commentId = commen.commentId, isDeleted = true)
                return true
            }
        }
        throw CommentNotFoundException("Not found Comment ID $commentId")
    }

    fun printComment(noteId: Int) {
        for ((index, commen) in comments.withIndex()) {
            if (commen.comId == noteId && !commen.isDeleted) {
                println("Comments for noteID $noteId -  " + commen.commentId + " * " + commen.ownerId + " * " + commen.message)
            }
        }
    }

    fun restoreComment(noteId: Int, commentId: Int): Boolean {
        for ((index, commRestore) in comments.withIndex()) {
            if (commRestore.comId == noteId && commRestore.commentId == commentId && commRestore.isDeleted) {
                comments[index] = commRestore.copy(isDeleted = false)
                return true
            }
        }
        throw CommentNotFoundException("Not found Comment ID $commentId")
    }

    fun getNote(ownerId: Int): List<Note> {
        for (note in notes) {
            if (ownerId == note.ownerId && !note.isDeleted) {
                return notes
            }
        }
        throw NoteNotFoundException("Not found Note")
    }

    fun getComments(id: Int): List<NoteСomment> {
        val comments = mutableListOf<NoteСomment>()
        for (note in notes) {
            if (note.noteId == id) {
                for (comment in note.comments) {
                    if (!comment.isDeleted) {
                        comments.add(comment)
                    }
                }
                return comments
            }
        }
        throw CommentNotFoundException("Not found Comments")
}


fun getById(id: Int): Note {
    for (note in notes) {
        if (id == note.noteId && !note.isDeleted) {
            return note
        }
    }
    throw NoteNotFoundException("Not found Note")
}

}