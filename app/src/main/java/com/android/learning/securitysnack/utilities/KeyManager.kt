package com.android.learning.securitysnack.utilities

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeyManager {

    private const val ALIAS = "room_db_aes_key"
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    fun getOrCreateSecretKey(): SecretKey {
        val ks = KeyStore.getInstance(ANDROID_KEY_STORE)
        val isKeyGenerated = ks.containsAlias(ALIAS)
        if (isKeyGenerated) {
            val entry = (ks.getEntry(ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
            return entry
        } else {
            val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,ANDROID_KEY_STORE)
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