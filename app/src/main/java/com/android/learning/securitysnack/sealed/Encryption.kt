package com.android.learning.securitysnack.sealed

sealed class Encryption {
    object Basic : Encryption()
    object Intermediate: Encryption()
    object DB: Encryption()

    override fun toString(): String {
        return when(this) {
            is Basic -> "Basic"
            is Intermediate -> "Intermediate"
            is DB -> "DB"
        }
    }
}