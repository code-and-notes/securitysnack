package com.android.learning.securitysnack.ui.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.learning.securitysnack.db.entity.NoteDTO
import com.android.learning.securitysnack.db.entity.SecureTitle

@Composable
fun ESPScreen(modifier: Modifier, sharedPreferences: SharedPreferences){
    var key by remember {  mutableStateOf("")}
    var value by remember {mutableStateOf("")}
    var getKey by remember { mutableStateOf("")}
    var getValue: String? by remember {  mutableStateOf("")}

        Row(modifier.fillMaxWidth()) {
            TextField(value = key, onValueChange ={key = it},modifier.weight(1f))
            TextField(value = value, onValueChange ={value = it},modifier.weight(1f))
            Button(onClick = { sharedPreferences.edit().putString(key,value).commit() },modifier.weight(1f)) {
                Icon(Icons.Default.Add,"add")
            }
        }
        Spacer(modifier.padding(10.dp))
        Row() {
            TextField(value = getKey, onValueChange ={getKey = it})
            Button(onClick = { getValue = sharedPreferences.getString(key,"") }) {
                Icon(Icons.Default.Add,"add")
            }
        }
        Log.w("ESP","this is get value $getValue")
        Text("This is value for the key")
        Text(getValue?:"this is nothing", fontSize = 20.sp)

    }

