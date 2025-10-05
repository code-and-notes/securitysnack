package com.android.learning.securitysnack.ui.activities

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
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.learning.securitysnack.database.AppDatabase
import com.android.learning.securitysnack.sealed.Screens
import com.android.learning.securitysnack.ui.screens.Notes
import com.android.learning.securitysnack.ui.theme.SecuritysnackTheme
import com.android.learning.securitysnack.utilities.AppLock
import com.android.learning.securitysnack.viewmodels.MainViewModel

class MainActivity : FragmentActivity() {
    var screens: Screens by  mutableStateOf(Screens.Home)

    lateinit var database: RoomDatabase

    lateinit var mainViewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "security-snack-db"
        ).build()
        mainViewModel = MainViewModel()
        AppLock.promptForAuthentication(this)
        setContent {
            SecuritysnackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when(screens) {
                        Screens.Home -> Home(modifier = Modifier.padding(innerPadding), goToNotes = {screens = Screens.Notes})
                        Screens.Notes -> Notes(modifier = Modifier.padding(innerPadding),mainViewModel,database as AppDatabase)
                    }

                }
            }
        }

    }
}


@Composable
fun Home( modifier: Modifier = Modifier, goToNotes: () -> Unit = {}) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start) {
        Text("Welcome to Security Snack!")
        Button(onClick = goToNotes) {
            Text("Go to Notes")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    SecuritysnackTheme {
        Home()
    }
}