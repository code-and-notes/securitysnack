package com.android.learning.securitysnack.ui

sealed class ComposeState {
    object Home: ComposeState()
    object ESP: ComposeState()
    object BioAuth: ComposeState()
    object Notes : ComposeState()
}