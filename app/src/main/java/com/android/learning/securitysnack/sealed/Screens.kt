package com.android.learning.securitysnack.sealed

sealed class Screens {
    object Home : Screens()
    object Notes : Screens()

    object ESP: Screens()

    object BiometricAuth: Screens()
}