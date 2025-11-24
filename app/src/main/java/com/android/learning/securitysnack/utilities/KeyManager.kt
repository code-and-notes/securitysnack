package com.android.learning.securitysnack.utilities

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeyManager {

    private const val ANDROID_KEY_STORE :String = "AndroidKeyStore"

    fun getOrGenerateSecureKey(alias:String , userAuth: Boolean = false,userAuthTime:Int = 5): SecretKey{
        val keystore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keystore.load(null)
        val isKeyGenerated = keystore.containsAlias(alias)
        if(isKeyGenerated){
            val key = (keystore.getEntry(alias,null) as KeyStore.SecretKeyEntry).secretKey
            return key
        }
        val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,ANDROID_KEY_STORE)
        val keyGeneratorSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(256)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setUserAuthenticationRequired(userAuth)
            .setUserAuthenticationParameters(userAuthTime, KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        kg.init(keyGeneratorSpec)
        return kg.generateKey()
    }

    fun getCipherOrThrow(alias: String,cipher: String): Cipher {
        val ks = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        val secretKey = ks.getKey(alias,null) as SecretKey
        return Cipher.getInstance(cipher).apply {
            init(Cipher.ENCRYPT_MODE,secretKey)
        }
    }
}