package com.android.learning.securitysnack.utilities

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

object AppLock {

    fun bioMetricAuthAllTime(activity: FragmentActivity,onSuccess:()->Unit = {}){

        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login for app")
            .setSubtitle("Enter credentials to open")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()
        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                    super.onAuthenticationSucceeded(result)
                }
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )
        biometricPrompt.authenticate(biometricPromptInfo)
    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun bioMetricAuthTimeBased(activity: FragmentActivity, onSuccess:()->Unit = {}){
        try {
            KeyManager.getDecryptCipherOrThrow()
            return
        }
        catch (e: Exception){
            KeyManager.getOrGenerateSecureKey(BioAuthEnum.ALIAS.value,true)
        }

        val cipher = try {
            KeyManager.getDecryptCipherOrThrow() } catch (_: Exception) { null }
        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login for app")
            .setSubtitle("Enter credentials to open")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()
        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                    super.onAuthenticationSucceeded(result)
                }
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )
        if(cipher!=null){
            biometricPrompt.authenticate(biometricPromptInfo, BiometricPrompt.CryptoObject(cipher))
        }else {
            biometricPrompt.authenticate(biometricPromptInfo)
        }
    }
}