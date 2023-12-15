import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



class NotesServiceTest {

    @before
    fun clearBeforeTest (){
        NotesService.clear()
    }

    @Test
    fun addNote() {
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val notes = NotesService

        notes.addNote(noteOne)
        assertEquals(Note(2,"Title", "Text", 0, 0, false), noteOne)
    }

    @Test
    fun editNote(){
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val noteNew = Note(2,"NewTitle", "NewText", 0, 0, false)
        notes.addNote(noteOne)

        assertEquals(notes.editNote(2, Note(2,"NewTitle", "NewText", 0, 0, false)), noteNew)
    }
    @Test
    fun removeNote(){
        clearBeforeTest()
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val noteTwo = Note(3,"Title", "Text", 0, 0, false)
        notes.addNote(noteOne)
        notes.addNote(noteTwo)

        notes.removeNote(2)
        val size = notes.notes.size

        assertEquals(size, 1)
    }

    @Test
    fun createComment(){
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val commentOne = NoteСomment(2,1,100,"NewComment",false)

        notes.addNote(noteOne)
        notes.createComment(2,commentOne)

        assertEquals(notes.createComment(2,NoteСomment(2,1,100,"NewComment",false)), commentOne)
    }

    @Test
    fun editComment(){
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val commentOne = NoteСomment(2,1,100,"NewComment",false)
        val editedComment = NoteСomment(2,1,100,"EditedComment",false)

        notes.addNote(noteOne)
        notes.createComment(2,commentOne)

        assertEquals(notes.editComment(1, NoteСomment(2,1,100,"EditedComment",false)), editedComment)
    }

    @Test
    fun removeComment(){
        clearBeforeTest()
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val commentOne = NoteСomment(2,1,100,"NewComment",false)
        val commentTwo = NoteСomment(2,2,100,"NewComment2",false)

        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        notes.createComment(2,commentTwo)

        notes.removeComment(2,2)
        val commentsSize = notes.comments.size

        assertEquals(commentsSize, 1)
    }

    @Test
    fun restoreComment(){
        clearBeforeTest()
        val notes = NotesService
        val noteOne = Note(2,"Title", "Text", 0, 0, false)
        val commentOne = NoteСomment(2,1,100,"NewComment",false)
        val commentTwo = NoteСomment(2,2,100,"NewComment2",false)

        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        notes.createComment(2,commentTwo)

        notes.removeComment(2,2)
        notes.restoreComment(2, 2)

        val commentsSize = notes.comments.size

        assertEquals(commentsSize, 2)
    }
}

annotation class before