package com.android.learning.securitysnack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.learning.securitysnack.db.converters.CryptoConverters
import com.android.learning.securitysnack.db.dao.NotesDao
import com.android.learning.securitysnack.db.entities.Notes

@Database(entities = [Notes::class], version = 1)
@TypeConverters(CryptoConverters::class)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

}