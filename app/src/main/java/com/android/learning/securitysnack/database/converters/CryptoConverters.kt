package com.android.learning.securitysnack.database.converters

import androidx.compose.runtime.simulateHotReload
import androidx.room.TypeConverter
import com.android.learning.securitysnack.database.entity.SecureString
import com.android.learning.securitysnack.sealed.Encryption
import com.android.learning.securitysnack.utilities.KeyManager
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class CryptoConverters {


    @TypeConverter
    fun toEncryptedString(secureString: SecureString?): ByteArray? {
        if (secureString == null) return null
        val key = KeyManager.getOrCreateSecretKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE,key)
        val iv = cipher.iv
        return iv + cipher.doFinal(secureString.value.toByteArray())
    }

    @TypeConverter
    fun toDecryptedString(blob: ByteArray?): SecureString? {
        if (blob == null) return null
        val key = KeyManager.getOrCreateSecretKey()
        val iv = blob.copyOfRange(0,12)
        val data = blob.copyOfRange(12,blob.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE,key, GCMParameterSpec(128, iv))
        return SecureString(String(cipher.doFinal(data), Charsets.UTF_8))
    }

    @TypeConverter
    fun fromEncryption(encryption: Encryption): String {
        return when (encryption) {
            is Encryption.DB -> "DB"
            is Encryption.Basic -> "Basic"
            is Encryption.Intermediate -> "Intermediate"
            // Add other types as needed
        }
    }

    @TypeConverter
    fun toEncryption(value: String): Encryption {
        return when (value) {
            "DB" -> Encryption.DB
            "Basic" -> Encryption.Basic
            "Intermediate" -> Encryption.Intermediate
            else -> Encryption.Basic
        }
    }


}