package com.android.learning.securitysnack.ui.sealed

sealed class ScreenState {
    object Home: ScreenState()
    object Notes: ScreenState()
    object ESP: ScreenState()
    object BioAuth: ScreenState()

}