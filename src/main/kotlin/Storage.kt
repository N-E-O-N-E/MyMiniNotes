
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Note(val content: String)

object NoteStorage {
    private val file = File("myMiniNotes.json")

    fun saveNotes(notes: List<Note>) {
        file.writeText(Json.encodeToString(notes))
    }

    fun loadNotes(): List<Note> {
        return if (file.exists()) {
            Json.decodeFromString(file.readText())
        } else {
            emptyList()
        }
    }
}