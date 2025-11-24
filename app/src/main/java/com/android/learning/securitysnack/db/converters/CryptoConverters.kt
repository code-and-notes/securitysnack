package com.android.learning.securitysnack.db.converters

import androidx.room.TypeConverter
import com.android.learning.securitysnack.db.entity.SecureTitle
import com.android.learning.securitysnack.utilities.KeyManager
import com.android.learning.securitysnack.utilities.enums.NotesEnum
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec


class CryptoConverters {

    @TypeConverter
    fun secureTitleToByteArray(secureTitle: SecureTitle): ByteArray {
        val secureKey = KeyManager.getOrGenerateSecureKey(NotesEnum.ALIAS.value)
        val cipher = Cipher.getInstance(NotesEnum.CIPHER.value)
        cipher.init(Cipher.ENCRYPT_MODE,secureKey)
        val iv = cipher.iv
        return  iv + cipher.doFinal(secureTitle.title.toByteArray())
    }

    @TypeConverter
    fun byteArrayToSecureTitle(byteArray: ByteArray): SecureTitle {
        val secureKey = KeyManager.getOrGenerateSecureKey(NotesEnum.ALIAS.value)
        val cipher = Cipher.getInstance(NotesEnum.CIPHER.value)
        val iv = byteArray.copyOfRange(0,12)
        val data = byteArray.copyOfRange(12,byteArray.size)
        cipher.init(Cipher.DECRYPT_MODE,secureKey, GCMParameterSpec(128,iv))
        val title = String(cipher.doFinal(data), Charsets.UTF_8)
        return SecureTitle(title)
    }
}