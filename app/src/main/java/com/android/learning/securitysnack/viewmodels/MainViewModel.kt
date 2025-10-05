package com.android.learning.securitysnack.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.android.learning.securitysnack.database.AppDatabase
import com.android.learning.securitysnack.database.entity.Note
import com.android.learning.securitysnack.database.entity.SecureString
import com.android.learning.securitysnack.sealed.Encryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.security.KeyPairGenerator

class MainViewModel: ViewModel() {

    fun saveNote(note: String,appDatabase: AppDatabase, encryption: Encryption = Encryption.Basic) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempNote = Note(content = SecureString(note), encryption = encryption)
            appDatabase.noteDao().insertNote(tempNote)
        }
    }

    suspend fun getAllNotes(appDatabase: AppDatabase): List<Note>  = viewModelScope.async(Dispatchers.IO) {
        val notes = appDatabase.noteDao().getAllNotes()
        notes
    }.await()

    suspend fun deleteNote(noteId: Int, appDatabase: AppDatabase) = viewModelScope.async(Dispatchers.IO) {
        appDatabase.noteDao().deleteNote(noteId)
    }



}