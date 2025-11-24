package com.android.learning.securitysnack.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.learning.securitysnack.db.dao.NoteDao
import com.android.learning.securitysnack.db.entity.NoteDTO
import com.android.learning.securitysnack.ui.ComposeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(val noteDao: NoteDao) : ViewModel() {

    private var notesInternal : MutableStateFlow<List<NoteDTO>> = MutableStateFlow(emptyList())

    val notes: StateFlow<List<NoteDTO>> = notesInternal

    var composeState: MutableStateFlow<ComposeState> = MutableStateFlow(ComposeState.Home)



    fun getNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            notesInternal.value = noteDao.getAll()
        }
    }
    fun saveNote(note: NoteDTO){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

}