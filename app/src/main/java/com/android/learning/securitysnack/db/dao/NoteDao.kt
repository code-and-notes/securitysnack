package com.android.learning.securitysnack.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.learning.securitysnack.db.entity.NoteDTO

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteDTO)

    @Query("SELECT * FROM noteDto")
    suspend fun getAll():List<NoteDTO>
}