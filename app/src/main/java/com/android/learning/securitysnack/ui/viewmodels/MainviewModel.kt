package com.android.learning.securitysnack.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.learning.securitysnack.db.dao.NotesDao
import com.android.learning.securitysnack.db.entities.Notes
import com.android.learning.securitysnack.ui.sealed.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainviewModel(val notesDao: NotesDao): ViewModel() {


    val screenState: MutableState<ScreenState> = mutableStateOf(ScreenState.Home)
    private var _notes: MutableStateFlow<List<Notes>>  = MutableStateFlow(emptyList())
    val notes : StateFlow<List<Notes>> = _notes

    fun getNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _notes.value = notesDao.getAllNotes()
        }
    }

    fun insertNote(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            notesDao.insertNote(notes)
        }
    }


}