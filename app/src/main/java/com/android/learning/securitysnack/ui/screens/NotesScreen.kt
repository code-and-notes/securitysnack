package com.android.learning.securitysnack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.unit.dp
import com.android.learning.securitysnack.db.entity.NoteDTO
import com.android.learning.securitysnack.db.entity.SecureTitle
import com.android.learning.securitysnack.ui.viewmodels.MainViewModel
import com.google.common.collect.Multimaps.index

@Composable
fun NotesScreen(modifier: Modifier,viewModel: MainViewModel){
    val notes by viewModel.notes.collectAsState()
    var note by remember {  mutableStateOf("")}
    Column(modifier = modifier.padding(5.dp)) {
        Row() {
            TextField(value = note, onValueChange ={note = it})
            Button(onClick = { viewModel.saveNote(NoteDTO(title = SecureTitle(note))) }) {
                Icon(Icons.Default.Add,"add")
            }
        }
        Button(onClick = {viewModel.getNotes()}) {
            Text("Click to see notes")
        }
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            items(notes.size){
                index->
                Text("${notes[index].title}")
            }
        }
    }

}