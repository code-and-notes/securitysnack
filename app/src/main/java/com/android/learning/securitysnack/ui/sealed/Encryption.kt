package com.android.learning.securitysnack.ui.sealed

sealed class Encryption {
    object Basic : Encryption()
    object Intermediate: Encryption()
}