package com.android.learning.securitysnack.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.android.learning.securitysnack.database.AppDatabase
import com.android.learning.securitysnack.database.entity.Note
import com.android.learning.securitysnack.sealed.Encryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.security.KeyPairGenerator

class MainViewModel: ViewModel() {

    fun saveNote(note: String,appDatabase: AppDatabase, encryption: Encryption = Encryption.Basic) {
        viewModelScope.launch(Dispatchers.IO) {
            val content = encryptNote(note)
            val tempNote = Note(content = content, encryption = encryption)
            appDatabase.noteDao().insertNote(tempNote)
        }
    }

    suspend fun getAllNotes(appDatabase: AppDatabase): List<Note>  = viewModelScope.async(Dispatchers.IO) {
        val notes = appDatabase.noteDao().getAllNotes()
        notes.forEach { note-> note.content = decryptNote(note.content) }
        notes
    }.await()

    suspend fun deleteNote(noteId: Int, appDatabase: AppDatabase) = viewModelScope.async(Dispatchers.IO) {
        appDatabase.noteDao().deleteNote(noteId)
    }

    private fun encryptNote(note: String): String {
        // Placeholder for encryption logic)
        return note.reversed()

    }
    private fun decryptNote(encryptedNote: String): String {
        // Placeholder for decryption logic
        return encryptedNote.reversed()
    }


}