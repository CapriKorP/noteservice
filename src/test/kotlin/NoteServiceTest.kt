import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


val notes = NotesService
val noteOne = Note(2,"Title", "Text", 0, 0, false)
val noteTwo = Note(3,"Title", "Text", 0, 0, false)
val noteNew = Note(2,"NewTitle", "NewText", 0, 0, false)
val commentOne = NoteСomment(2,1,100,"NewComment",false)
val commentTwo = NoteСomment(2,2,100,"NewComment2",false)
val editedComment = NoteСomment(2,1,100,"EditedComment",false)


class NotesServiceTest {

    @before
    fun clearBeforeTest (){
        NotesService.clear()
    }

    annotation class before

    @Test
    fun addNote() {
        notes.addNote(noteOne)
        assertEquals(Note(2,"Title", "Text", 0, 0, false), noteOne)
    }

    @Test
    fun editNote(){
        notes.addNote(noteOne)
        assertEquals(notes.editNote(2, Note(2,"NewTitle", "NewText", 0, 0, false)), noteNew)
    }
    @Test
    fun removeNote(){
        clearBeforeTest()
        notes.addNote(noteOne)
        notes.addNote(noteTwo)

        notes.removeNote(2)

        val actual = notes.notes.size

        assertEquals(1,actual)
    }

    @Test
    fun createComment(){
        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        val actual = notes.createComment(2,NoteСomment(2,1,100,"NewComment",false))

        assertEquals(commentOne, actual)
    }

    @Test
    fun editComment(){
        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        val actual = notes.editComment(1, NoteСomment(2,1,100,"EditedComment",false))

        assertEquals(editedComment, actual)
    }

    @Test
    fun removeComment(){
        clearBeforeTest()
        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        notes.removeComment(2,1)

        val expected = NoteСomment(2,1,100,"NewComment",true)
        val actual = notes.comments.last()

        assertEquals(expected, actual)
    }

    @Test
    fun restoreComment(){
        clearBeforeTest()
        notes.addNote(noteOne)
        notes.createComment(2,commentOne)
        notes.createComment(2,commentTwo)

        notes.removeComment(2,2)
        notes.restoreComment(2, 2)

        val actual = notes.comments.size

        assertEquals(2, actual)
    }
}