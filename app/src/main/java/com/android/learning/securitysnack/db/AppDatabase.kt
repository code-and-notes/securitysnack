package com.android.learning.securitysnack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.learning.securitysnack.db.converters.CryptoConverters
import com.android.learning.securitysnack.db.dao.NoteDao
import com.android.learning.securitysnack.db.entity.NoteDTO

@Database(entities = [NoteDTO::class], version = 1, exportSchema = false)
@TypeConverters(CryptoConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun provideNoteDao(): NoteDao

}