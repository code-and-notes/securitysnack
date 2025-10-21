package com.android.learning.securitysnack.ui.screens

import android.text.BoringLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.learning.securitysnack.db.entities.Notes
import com.android.learning.securitysnack.db.entities.SecuredTitle
import com.android.learning.securitysnack.ui.viewmodels.MainviewModel

@Composable
fun NotesScreen(modifier: Modifier = Modifier, backClick:()->Unit = {},mainviewModel: MainviewModel){
    Column(
        modifier =modifier.padding(5.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        var title: String by remember { mutableStateOf("") }
        val notes:List<Notes> by mainviewModel.notes.collectAsState()
        var showNotes : Boolean by remember { mutableStateOf(false) }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(title, onValueChange = { title = it })
            Button(onClick = {
                mainviewModel.insertNote(Notes(title = SecuredTitle(title)))
                title = ""
            }) {
                Text("Save")
            }
        }
        Text("Your notes will be stored Locally on your device")
        Button(onClick = {
            showNotes = !showNotes
            mainviewModel.getNotes()
        }) {
            Text("View Saved Notes or Hide")
        }
        if(showNotes) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                items(notes.size) { index ->
                    Text(notes[index].toString())
                }
            }
        }
    }

}