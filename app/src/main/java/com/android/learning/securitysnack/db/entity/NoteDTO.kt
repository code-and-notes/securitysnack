package com.android.learning.securitysnack.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDTO(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title: SecureTitle,
    val isChecked: Boolean = false
)

data class SecureTitle(val title: String){

    override fun toString(): String {
        return title
    }
}