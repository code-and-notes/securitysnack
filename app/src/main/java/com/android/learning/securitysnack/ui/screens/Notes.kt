package com.android.learning.securitysnack.ui.screens

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.learning.securitysnack.database.AppDatabase
import com.android.learning.securitysnack.database.entity.Note
import com.android.learning.securitysnack.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Notes(modifier: Modifier = Modifier,mainViewModel: MainViewModel,database: AppDatabase) {
    val scope = rememberCoroutineScope()
    var noteText by remember { mutableStateOf("") }
    var notes : List<Note> by remember { mutableStateOf(emptyList()) }
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start) {
        Text("Enter your notes below:")
        Spacer(modifier = Modifier.padding(8.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            TextField(
                value = noteText,
                onValueChange = { newText -> noteText = newText },
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                mainViewModel.saveNote(noteText,database)
                noteText = ""
            }) {
                Text("Save")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text("Your notes will be saved locally on your device.")
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick =
            {
                scope.launch(Dispatchers.IO) {
                    notes = mainViewModel.getAllNotes(database)
                }
            }
        ) {
            Text("View Saved Notes")
        }
        ShowNotes(notes, delete = { noteId ->
            scope.launch {
                mainViewModel.deleteNote(noteId, database)
                notes = mainViewModel.getAllNotes(database)
            }
        })
    }
}

@Composable
fun ShowNotes(notes: List<Note>, delete: (Int) -> Unit = {}) {
    LazyColumn {
        items(notes.size) { index ->
            BaiscNoteItem(note = notes[index],delete = { delete(notes[index].id) })
        }
    }
}
@Composable
fun BaiscNoteItem(note: Note,delete: () -> Unit = {}) {
    Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = note.content, modifier = Modifier.weight(1f))
        Button(onClick = delete) {
            Text("Delete")
        }

    }
}
