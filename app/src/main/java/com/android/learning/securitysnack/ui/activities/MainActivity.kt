package com.android.learning.securitysnack.ui.activities

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.android.learning.securitysnack.db.NotesDatabase
import com.android.learning.securitysnack.ui.screens.NotesScreen
import com.android.learning.securitysnack.ui.sealed.ScreenState

import com.android.learning.securitysnack.ui.theme.SecuritysnackTheme
import com.android.learning.securitysnack.ui.viewmodels.MainviewModel
import com.android.learning.securitysnack.ui.viewmodels.MainviewModelFactory


class MainActivity : FragmentActivity() {

    lateinit var mainviewModel: MainviewModel

    lateinit var database : NotesDatabase

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
        val factory = MainviewModelFactory(database.notesDao())
        mainviewModel = ViewModelProvider.create(this,factory)[MainviewModel::class]
        setContent {
            val screenState by remember { mainviewModel.screenState }
            val backClick = { mainviewModel.screenState.value = ScreenState.Home }
            SecuritysnackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    when(screenState){
                        is ScreenState.Home -> Home(modifier,mainviewModel)
                        is ScreenState.Notes -> NotesScreen(modifier,backClick,mainviewModel)
                        is ScreenState.ESP -> {}
                        is ScreenState.BioAuth -> {}
                    }
                    BackHandler {
                        backClick()
                    }
                }
            }
        }

    }
}

@Composable
fun Home( modifier: Modifier = Modifier,
          mainviewModel: MainviewModel
) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text("Welcome to Security Snack!")
        Button(onClick = {mainviewModel.screenState.value = ScreenState.Notes}) {
            Text("Go to notes screen")
        }


    }

}
