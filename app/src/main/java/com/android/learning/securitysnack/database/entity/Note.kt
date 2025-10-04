package com.android.learning.securitysnack.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.learning.securitysnack.database.converters.CryptoConverters
import com.android.learning.securitysnack.sealed.Encryption

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @field:TypeConverters(CryptoConverters::class)
    var content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val encryption: Encryption
)