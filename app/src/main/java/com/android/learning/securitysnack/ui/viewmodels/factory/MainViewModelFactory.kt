package com.android.learning.securitysnack.ui.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.learning.securitysnack.db.dao.NoteDao
import com.android.learning.securitysnack.ui.viewmodels.MainViewModel

class MainViewModelFactory(val noteDao: NoteDao): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}