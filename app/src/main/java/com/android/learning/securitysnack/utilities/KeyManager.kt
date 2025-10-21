package com.android.learning.securitysnack.utilities

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.CreationExtras
import java.security.AlgorithmParameters
import java.security.KeyStore
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeyManager {
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"

    @RequiresApi(Build.VERSION_CODES.R)
    fun getOrGenerateSecureKey(alias : String, userAuth: Boolean = false, userAuthTime:Int = 5): SecretKey {
        val keystore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keystore.load(null)
        val isKeyGenerated = keystore.containsAlias(alias)
        if(isKeyGenerated){
            val key = (keystore.getEntry(alias,null) as KeyStore.SecretKeyEntry).secretKey
            return key
        }
        val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,ANDROID_KEY_STORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setUserAuthenticationRequired(userAuth)
            .setUserAuthenticationParameters(userAuthTime,
                KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL
            )
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()
        kg.init(keyGenParameterSpec)
        return kg.generateKey()
    }

    fun getDecryptCipherOrThrow(): Cipher {
        val ks = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        val secretKey = ks.getKey(BioAuthEnum.ALIAS.value,null) as SecretKey
        return Cipher.getInstance(BioAuthEnum.CIPHER.value).apply {
            init(Cipher.ENCRYPT_MODE,secretKey)
        }
    }
}