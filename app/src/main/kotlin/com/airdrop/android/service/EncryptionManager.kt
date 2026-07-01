package com.airdrop.android.service

import android.util.Log
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom

class EncryptionManager {

    private val TAG = "EncryptionManager"
    private val ALGORITHM = "AES"
    private val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private val KEY_SIZE = 256

    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(KEY_SIZE, SecureRandom())
        return keyGenerator.generateKey()
    }

    fun encryptFile(inputFile: File, outputFile: File, key: SecretKey): ByteArray? {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val iv = cipher.iv
            
            FileInputStream(inputFile).use { fis ->
                FileOutputStream(outputFile).use { fos ->
                    fos.write(iv)
                    
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    
                    while (fis.read(buffer).also { bytesRead = it } != -1) {
                        val encryptedBlock = cipher.update(buffer, 0, bytesRead)
                        fos.write(encryptedBlock)
                    }
                    
                    val finalBlock = cipher.doFinal()
                    fos.write(finalBlock)
                }
            }
            
            iv.also { Log.d(TAG, "File encrypted successfully") }
        } catch (e: Exception) {
            Log.e(TAG, "Error encrypting file: ${e.message}")
            null
        }
    }

    fun decryptFile(inputFile: File, outputFile: File, key: SecretKey): Boolean {
        return try {
            FileInputStream(inputFile).use { fis ->
                val iv = ByteArray(16)
                fis.read(iv)
                
                val cipher = Cipher.getInstance(TRANSFORMATION)
                cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
                
                FileOutputStream(outputFile).use { fos ->
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    
                    while (fis.read(buffer).also { bytesRead = it } != -1) {
                        val decryptedBlock = cipher.update(buffer, 0, bytesRead)
                        fos.write(decryptedBlock)
                    }
                    
                    val finalBlock = cipher.doFinal()
                    fos.write(finalBlock)
                }
            }
            
            Log.d(TAG, "File decrypted successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error decrypting file: ${e.message}")
            false
        }
    }

    fun keyToString(key: SecretKey): String {
        return android.util.Base64.encodeToString(key.encoded, android.util.Base64.DEFAULT)
    }

    fun stringToKey(keyString: String): SecretKey {
        val decodedKey = android.util.Base64.decode(keyString, android.util.Base64.DEFAULT)
        return javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.size, ALGORITHM)
    }
}
