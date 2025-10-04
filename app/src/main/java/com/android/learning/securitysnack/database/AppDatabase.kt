package com.android.learning.securitysnack.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.learning.securitysnack.database.dao.NoteDao
import com.android.learning.securitysnack.database.entity.Note


@Database(entities = [Note::class], version =1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

}