package com.android.learning.securitysnack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Notes(modifier: Modifier = Modifier) {
     var noteText by remember { mutableStateOf("") }
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start) {
        Text("Enter your notes below:")
        Spacer(modifier = Modifier.padding(8.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            TextField(
                value = noteText,
                onValueChange = { newText -> noteText = newText },
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {}) {
                Text("Save")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text("Your notes will be saved locally on your device.")
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {}) {
            Text("View Saved Notes")
        }

    }
}

@Composable
@Preview(showBackground = true)
fun NotesPreview() {
    Notes()
}