import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

@Preview
@Composable
fun App() {
    var notes by remember { mutableStateOf(NoteStorage.loadNotes().toMutableList()) }
    var newNote by remember { mutableStateOf("") }

    Box {

        Image(
            painter = painterResource("paper.jpg"),
            contentDescription = "Wallpaper",
            contentScale = ContentScale.FillBounds,
        )

        MaterialTheme {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "MyMiniNotes", style = MaterialTheme.typography.h4)
                Divider(modifier = Modifier.padding(vertical = 10.dp))

                Row {
                    TextField(
                        value = newNote,
                        onValueChange = { newNote = it },
                        placeholder = { Text("Neue Notiz anlegen...") },
                        modifier = Modifier.weight(0.8f)
                    )
                    Button(
                        onClick = {
                            if (newNote.isNotBlank()) {
                                notes = notes.toMutableList().apply { add(Note(newNote)) }
                                NoteStorage.saveNotes(notes)
                                newNote = ""
                            }
                        },
                        modifier = Modifier.padding(10.dp).weight(0.2f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 20, green = 80, blue = 130)),
                    ) {
                        Text("Speichern", color = Color.White)
                    }

                }


                Divider(modifier = Modifier.padding(vertical = 10.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn {
                        items(notes) { note ->


                            Row(
                                modifier = Modifier.fillMaxWidth().padding(5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Surface(
                                    modifier = Modifier.weight(0.9f),
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.Black.copy(alpha = 0.1f)
                                ) {
                                    Text(
                                        modifier = Modifier.weight(0.9f).padding(10.dp),
                                        text = note.content
                                    )
                                }

                                Button(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .padding(start = 10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(red = 130, green = 30, blue = 50)
                                    ),
                                    onClick = {
                                        notes = notes.toMutableList().also { it.remove(note) }
                                        NoteStorage.saveNotes(notes)
                                    }
                                ) {
                                    Image(
                                        painter = painterResource("delete.png"),
                                        contentDescription = "Delete"
                                    )
                                }
                            }

                            Divider()
                        }
                    }
                }

            }
        }
    }
}

fun main() = application {
    Window(
        state = WindowState(width = 700.dp, height = 700.dp),
        onCloseRequest = ::exitApplication,
        title = "MyMiniNotes - © Markus Wirtz",
        resizable = false,
        icon = null,
    ) {
        App()
    }
}
