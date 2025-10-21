package com.android.learning.securitysnack.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : SecuredTitle ,
    val timestamp: Long = System.currentTimeMillis()
){

    override fun toString(): String {
        return title.toString()
    }
}

data class SecuredTitle(val title: String){

    override fun toString(): String {
        return title
    }
}