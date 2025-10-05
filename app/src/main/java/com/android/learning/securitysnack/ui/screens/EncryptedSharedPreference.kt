package com.android.learning.securitysnack.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.core.content.edit

@Composable
fun ESPScreen(pref: SharedPreferences){
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var getKey:String by remember { mutableStateOf("") }
    var valueFromSP: String by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Encrypted Shared Preferences Scree")
        Text("Enter key and value for saving in Encrypted Shared Preferences")
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            TextField(value = key, onValueChange = { newText -> key = newText }, modifier = Modifier.weight(1f))
            Spacer(Modifier.size(2.dp))
            TextField(value = value, onValueChange = { newText -> value = newText }, modifier = Modifier.weight(1f))
            Button(onClick = {
                pref.edit { putString(key, value) }
                key = ""
                value = ""
            }) {
                Text("Save")
            }
        }
        Spacer(Modifier.size(10.dp))
        Text("Get encrypted value from Shared Preferences")
        Spacer(Modifier.size(10.dp))
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            TextField(value = getKey, onValueChange = { newText -> getKey = newText }, modifier = Modifier.weight(1f))
            Button(onClick = {
                valueFromSP = pref.getString(getKey,"No Value").toString()
            }) {
                Text("Get Value")
            }
            Text(valueFromSP)
        }
    }

}