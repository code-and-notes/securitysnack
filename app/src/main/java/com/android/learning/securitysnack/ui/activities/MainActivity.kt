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
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.android.learning.securitysnack.db.NotesDatabase
import com.android.learning.securitysnack.ui.screens.BioAuthScreen
import com.android.learning.securitysnack.ui.screens.ESPScreen
import com.android.learning.securitysnack.ui.screens.NotesScreen
import com.android.learning.securitysnack.ui.sealed.ScreenState

import com.android.learning.securitysnack.ui.theme.SecuritysnackTheme
import com.android.learning.securitysnack.ui.viewmodels.MainviewModel
import com.android.learning.securitysnack.ui.viewmodels.MainviewModelFactory
import com.android.learning.securitysnack.utilities.ESPenum


class MainActivity : FragmentActivity() {

    lateinit var mainviewModel: MainviewModel

    lateinit var database : NotesDatabase

    lateinit var masterKey: MasterKey
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.R)
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
        masterKey = MasterKey.Builder(this, ESPenum.ALIAS.value)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            ESPenum.FILE_NAME.value,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        setContent {
            val screenState by remember { mainviewModel.screenState }
            val backClick = { mainviewModel.screenState.value = ScreenState.Home }
            SecuritysnackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    when(screenState){
                        is ScreenState.Home -> Home(modifier,mainviewModel)
                        is ScreenState.Notes -> NotesScreen(modifier,mainviewModel)
                        is ScreenState.ESP -> {
                            ESPScreen(modifier,sharedPreferences)
                        }
                        is ScreenState.BioAuth -> {
                            BioAuthScreen(modifier)
                        }
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
        Button(onClick = {mainviewModel.screenState.value = ScreenState.ESP}) {
            Text("Go to encrypted shared pref screen")
        }
        Button(onClick = {mainviewModel.screenState.value = ScreenState.BioAuth}) {
            Text("Go to BioAuth screen")
        }


    }

}
