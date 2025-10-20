package com.android.learning.securitysnack.ui.activities

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity

import com.android.learning.securitysnack.ui.theme.SecuritysnackTheme


class MainActivity : FragmentActivity() {


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecuritysnackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(modifier = Modifier.padding(innerPadding))


                }
            }
        }

    }
}


@Composable
fun Home( modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start) {
        Text("Welcome to Security Snack!")
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    SecuritysnackTheme {
        Home()
    }
}