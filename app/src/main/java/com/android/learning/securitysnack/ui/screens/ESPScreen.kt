package com.android.learning.securitysnack.ui.screens

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.edit

@Composable
fun ESPScreen(modifier: Modifier,sharedPreferences: SharedPreferences){
    var key1 : String by remember { mutableStateOf("") }
    var key2 : String by remember { mutableStateOf("") }
    var value :String by remember { mutableStateOf("") }
    var keyValue: String by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is Encrypted shared preferences screen ")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(value = key1 , onValueChange = {key1 = it},modifier = Modifier.weight(2f))
            TextField(value = value , onValueChange = {value = it},modifier = Modifier.weight(2f))
            Button(
                modifier = modifier.weight(1f),
                onClick = {
                sharedPreferences.edit(commit = true) { putString(key1, value) }
                key1 = ""
                value = ""
            }) {
                Text("Save")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(value = key2 , onValueChange = {key2 = it},modifier = Modifier.weight(2f))
            Button(
                modifier = modifier.weight(1f),
                onClick = {
                keyValue = sharedPreferences.getString(key2,"").toString()
                key2 = ""
            }) {
                Text("Get")
            }
        }
        Text("This is the value key - $key2 and value - $keyValue")

    }
}