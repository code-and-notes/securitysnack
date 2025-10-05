package com.android.learning.securitysnack.utilities

import android.app.Activity
import android.os.Build
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.android.learning.securitysnack.utilities.TimeBasedKeyManager.getEncryptCipherOrThrow

object AppLock {

    @RequiresApi(Build.VERSION_CODES.P)
    fun promptForAuthentication(activity: FragmentActivity,
                                onSuccess: (() -> Unit) = {}
                                ){
        val executor = activity.mainExecutor
        val applicationContext = activity.applicationContext
        val biometricPrompt = BiometricPrompt(
            activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                    activity.finish()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    onSuccess.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

       biometricPrompt.authenticate(promptInfo)

    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun promptForAuthenticationTimeBased(
        activity: FragmentActivity,
        onSuccess: () -> Unit
    ) {
        try {
            val tmp = getEncryptCipherOrThrow()
            onSuccess()
            return
        } catch (e: UserNotAuthenticatedException) {
            Log.w("AppLock", "User not authenticated; need to re-auth")
        } catch (e: KeyPermanentlyInvalidatedException) {
            Log.w("AppLock", "Key invalidated; likely due to new biometric enrollment")
            TimeBasedKeyManager.getOrCreateSecretKeyTime()
        } catch (e: Exception) {
            Log.e("AppLock", "Failed to init cipher: $e")
            TimeBasedKeyManager.getOrCreateSecretKeyTime()
        }


        val cipher = try { getEncryptCipherOrThrow() } catch (_: Exception) { null }

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock")
            .setSubtitle("Use biometric or device PIN")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        val prompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {

                    onSuccess()
                }
                override fun onAuthenticationError(code: Int, msg: CharSequence) {
                    Log.e("AppLock", "Auth error $code: $msg")
                }
                override fun onAuthenticationFailed() { }
            }
        )


        if (cipher != null) {
            prompt.authenticate(info, BiometricPrompt.CryptoObject(cipher))
        } else {
            prompt.authenticate(info)
        }
    }


}