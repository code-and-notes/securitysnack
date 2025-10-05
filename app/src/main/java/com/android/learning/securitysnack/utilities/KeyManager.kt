package com.android.learning.securitysnack.utilities

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeyManager {

    private const val ALIAS = "room_db_aes_key"
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    fun getOrCreateSecretKey(): SecretKey {
        val ks = KeyStore.getInstance(ANDROID_KEY_STORE)
        ks.load(null)
        val isKeyGenerated = ks.containsAlias(ALIAS)
        if (isKeyGenerated) {
            val entry = (ks.getEntry(ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
            return entry
        } else {
            val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()
            kg.init(keyGenParameterSpec)
            return kg.generateKey()
        }
    }
}
object TimeBasedKeyManager {
    private const val ALIAS_TIME_BASED_AUTH = "app_ba_session_key"
    private const val AUTH_DURATION_SECONDS = 3
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    @RequiresApi(Build.VERSION_CODES.R)
    fun getOrCreateSecretKeyTime(): SecretKey {
        val ks = KeyStore.getInstance(ANDROID_KEY_STORE)
        ks.load(null)
        val isKeyGenerated = ks.containsAlias(ALIAS_TIME_BASED_AUTH)
        if (isKeyGenerated) {
            val entry = (ks.getEntry(ALIAS_TIME_BASED_AUTH, null) as KeyStore.SecretKeyEntry).secretKey
            return entry
        } else {
            val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,ANDROID_KEY_STORE)
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                ALIAS_TIME_BASED_AUTH,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(true)
                .setUserAuthenticationParameters(AUTH_DURATION_SECONDS,
                    KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL)
                .setKeySize(256)
                .build()
            kg.init(keyGenParameterSpec)
            return kg.generateKey()
        }
    }

    fun getEncryptCipherOrThrow(): Cipher {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val key = (keyStore.getKey(ALIAS_TIME_BASED_AUTH, null) as SecretKey)

        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(Cipher.ENCRYPT_MODE, key) // may throw if not authenticated
        }
    }

    fun getDecryptCipherOrThrow(iv: ByteArray): Cipher {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val key = (keyStore.getKey(ALIAS_TIME_BASED_AUTH, null) as SecretKey)

        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            val spec = GCMParameterSpec(128, iv)
            init(Cipher.DECRYPT_MODE, key, spec) // may throw if not authenticated
        }
    }


}