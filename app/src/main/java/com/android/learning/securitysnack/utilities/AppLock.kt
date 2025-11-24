package com.android.learning.securitysnack.utilities

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.android.learning.securitysnack.ui.activities.MainActivity
import com.android.learning.securitysnack.utilities.enums.BioAuthEnum

object AppLock {

    fun bioMetricAuthAllTime(activity: FragmentActivity,onSuccess:()-> Unit){
        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("This is ALL time biometric path")
            .setDescription("This is test info for all time biometric path info")
            .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.w("AppLock","Auth is failed")
                    super.onAuthenticationError(errorCode, errString)
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                    super.onAuthenticationSucceeded(result)
                }
            }
        )
        biometricPrompt.authenticate(biometricPromptInfo)
    }

    fun bioMetricTimeBased(activity: FragmentActivity,onSuccess: () -> Unit){
        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("This is time biometric path")
            .setDescription("This is test info for all time biometric path info")
            .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
        try{
            KeyManager.getCipherOrThrow(BioAuthEnum.ALIAS.value, BioAuthEnum.CIPHER.value)
            return
        }
        catch (e: Exception){
            KeyManager.getOrGenerateSecureKey(BioAuthEnum.ALIAS.value,true)
        }

        val cipher = try {
            KeyManager.getCipherOrThrow(BioAuthEnum.ALIAS.value, BioAuthEnum.CIPHER.value)
        } catch (_: Exception){
            null
        }
        Log.w("TimeBased","this is cipher $cipher")
        val biometricPrompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.w("AppLock","Auth is failed")
                    super.onAuthenticationError(errorCode, errString)
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                    super.onAuthenticationSucceeded(result)
                }
            }
        )
        if(cipher == null){
            biometricPrompt.authenticate(biometricPromptInfo)
        }
        else{
            biometricPrompt.authenticate(biometricPromptInfo, BiometricPrompt.CryptoObject(cipher))
        }

    }
}