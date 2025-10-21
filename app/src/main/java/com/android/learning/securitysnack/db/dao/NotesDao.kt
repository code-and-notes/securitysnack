package com.android.learning.securitysnack.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.android.learning.securitysnack.db.entities.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(notes: Notes)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Notes>
}