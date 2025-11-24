package com.android.learning.securitysnack.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.learning.securitysnack.ui.ComposeState
import com.android.learning.securitysnack.ui.viewmodels.MainViewModel

@Composable
fun HomeScreen(modifier: Modifier,viewModel: MainViewModel,sharedPreferences: SharedPreferences){
    Column(modifier = modifier.padding(2.dp)) {
        Button(onClick = {viewModel.composeState.value = ComposeState.Notes}) {
            Text("Go to Notes screen")
        }
        Button(onClick = {viewModel.composeState.value = ComposeState.ESP}) {
            Text("Go to ESP screen")
        }
        Button(onClick = {viewModel.composeState.value = ComposeState.BioAuth}) {
            Text("Go to Bio auth screen")
        }

    }

}