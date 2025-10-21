package com.android.learning.securitysnack.db.converters

import android.icu.text.CaseMap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.android.learning.securitysnack.db.entities.SecuredTitle
import com.android.learning.securitysnack.utilities.KeyManager
import com.android.learning.securitysnack.utilities.NotesEnum
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.R)
class CryptoConverters {

    @TypeConverter
    fun secureTitleToTitle(secureTitle: SecuredTitle): ByteArray {
        val key = KeyManager.getOrGenerateSecureKey(NotesEnum.ALIAS.value)
        val cipher = Cipher.getInstance(NotesEnum.CIPHER.value)
        cipher.init(Cipher.ENCRYPT_MODE,key)
        val iv = cipher.iv
        return iv + cipher.doFinal(secureTitle.title.toByteArray())

    }
    @TypeConverter
    fun titleToSecureTitle(blob : ByteArray): SecuredTitle{
        val key = KeyManager.getOrGenerateSecureKey(NotesEnum.ALIAS.value)
        val cipher = Cipher.getInstance(NotesEnum.CIPHER.value)
        val iv = blob.copyOfRange(0,12)
        val data = blob.copyOfRange(12,blob.size)
        cipher.init(Cipher.DECRYPT_MODE,key, GCMParameterSpec(128,iv))
        return SecuredTitle(title = String(cipher.doFinal(data), Charsets.UTF_8))

    }
}