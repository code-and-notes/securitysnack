package com.android.learning.securitysnack.database.dao

import androidx.compose.ui.text.style.LineBreak
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.learning.securitysnack.database.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAllNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)

}