package com.android.learning.securitysnack.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.learning.securitysnack.db.dao.NotesDao

class MainviewModelFactory(
    private val notesDao: NotesDao
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainviewModel::class.java)){
            return MainviewModel(notesDao = notesDao) as T
        }
        throw IllegalArgumentException("not proper class")
    }
}