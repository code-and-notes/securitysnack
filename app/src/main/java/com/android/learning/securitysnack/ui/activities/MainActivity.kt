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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.android.learning.securitysnack.SecuritySnack
import com.android.learning.securitysnack.db.AppDatabase
import com.android.learning.securitysnack.db.dao.NoteDao
import com.android.learning.securitysnack.ui.ComposeState
import com.android.learning.securitysnack.ui.screens.BioAuthScreen
import com.android.learning.securitysnack.ui.screens.ESPScreen
import com.android.learning.securitysnack.ui.screens.HomeScreen
import com.android.learning.securitysnack.ui.screens.NotesScreen

import com.android.learning.securitysnack.ui.theme.SecuritysnackTheme
import com.android.learning.securitysnack.ui.viewmodels.MainViewModel
import com.android.learning.securitysnack.ui.viewmodels.factory.MainViewModelFactory
import com.android.learning.securitysnack.utilities.enums.ESPEnum


class MainActivity : FragmentActivity() {

    lateinit var database: AppDatabase

    lateinit var noteDao: NoteDao

    lateinit var viewModel: MainViewModel

    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
        noteDao = database.provideNoteDao()
        val factory = MainViewModelFactory(noteDao)
        viewModel = ViewModelProvider.create(this,factory)[MainViewModel::class]
        val masterKey = MasterKey.Builder(
            this,
            ESPEnum.ALIAS.value
        ).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            ESPEnum.FILE_NAME.value,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        setContent {
            SecuritysnackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    SecuritySnack(modifier = Modifier.padding(innerPadding), viewModel,sharedPreferences)



                }
            }
        }

    }
}


@Composable
fun SecuritySnack( modifier: Modifier = Modifier,
          viewModel: MainViewModel,
                   sharedPreferences: SharedPreferences) {
    val composeState = viewModel.composeState.collectAsState()
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.Start) {
        Text("Welcome to Security Snack!")
        when(composeState.value){
            is ComposeState.Home -> HomeScreen(modifier,viewModel, sharedPreferences)
            is ComposeState.ESP -> ESPScreen(modifier,sharedPreferences)
            is ComposeState.Notes -> NotesScreen(modifier,viewModel)
            is ComposeState.BioAuth -> BioAuthScreen(modifier)
        }

    }

}

