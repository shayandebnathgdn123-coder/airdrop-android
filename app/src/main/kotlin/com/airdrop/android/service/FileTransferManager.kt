package com.airdrop.android.service

import android.content.Context
import android.util.Log
import com.airdrop.android.data.model.Device
import com.airdrop.android.data.model.FileTransfer
import com.airdrop.android.data.model.SharedFile
import com.airdrop.android.data.model.TransferStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileTransferManager(private val context: Context) {

    private val _transfers = MutableStateFlow<List<FileTransfer>>(emptyList())
    val transfers: StateFlow<List<FileTransfer>> = _transfers.asStateFlow()

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    private val _nfcAutoShareEnabled = MutableStateFlow(true)
    val nfcAutoShareEnabled: StateFlow<Boolean> = _nfcAutoShareEnabled.asStateFlow()

    private val TAG = "FileTransferManager"
    private val SERVER_PORT = 8888
    private val QUEUE_SIZE = 50

    private val queuedFilesForAutoShare = mutableListOf<SharedFile>()

    fun queueFileForAutoShare(file: SharedFile) {
        if (queuedFilesForAutoShare.size < QUEUE_SIZE) {
            queuedFilesForAutoShare.add(file)
            Log.d(TAG, "File queued for auto-share: ${file.fileName}")
        }
    }

    fun getQueuedFilesForAutoShare(): List<SharedFile> {
        return queuedFilesForAutoShare.toList()
    }

    fun clearQueuedFiles() {
        queuedFilesForAutoShare.clear()
        Log.d(TAG, "Queued files cleared")
    }

    fun startAutoTransferToDevice(files: List<SharedFile>, targetDevice: Device) {
        files.forEach { file ->
            startTransfer(file, targetDevice)
        }
    }

    fun startTransfer(file: SharedFile, targetDevice: Device) {
        val transfer = FileTransfer(
            transferId = System.currentTimeMillis().toString(),
            file = file,
            targetDevice = targetDevice,
            status = TransferStatus.PENDING
        )

        _transfers.value = _transfers.value + transfer
        Log.d(TAG, "Transfer started: ${file.fileName} to ${targetDevice.deviceName}")

        // TODO: Implement actual file transfer logic
        // This would involve:
        // 1. Connect to target device via WiFi Direct
        // 2. Send file in chunks
        // 3. Update progress
        // 4. Handle errors
    }

    fun cancelTransfer(transferId: String) {
        _transfers.value = _transfers.value.map { transfer ->
            if (transfer.transferId == transferId) {
                transfer.copy(status = TransferStatus.CANCELLED)
            } else {
                transfer
            }
        }
    }

    fun addDevice(device: Device) {
        if (!_devices.value.any { it.deviceAddress == device.deviceAddress }) {
            _devices.value = _devices.value + device
            Log.d(TAG, "Device added: ${device.deviceName}")
        }
    }

    fun removeDevice(deviceAddress: String) {
        _devices.value = _devices.value.filterNot { it.deviceAddress == deviceAddress }
        Log.d(TAG, "Device removed: $deviceAddress")
    }

    fun connectToDevice(device: Device) {
        Log.d(TAG, "Connecting to device: ${device.deviceName}")
    }

    fun disconnectFromDevice(deviceAddress: String) {
        Log.d(TAG, "Disconnecting from device: $deviceAddress")
    }

    fun updateProgress(transferId: String, progress: Float) {
        _transfers.value = _transfers.value.map { transfer ->
            if (transfer.transferId == transferId) {
                transfer.copy(
                    progress = progress.coerceIn(0f, 1f),
                    status = when {
                        progress >= 1f -> TransferStatus.COMPLETED
                        progress > 0f -> TransferStatus.IN_PROGRESS
                        else -> TransferStatus.PENDING
                    }
                )
            } else {
                transfer
            }
        }
    }

    fun setNFCAutoShareEnabled(enabled: Boolean) {
        _nfcAutoShareEnabled.value = enabled
        Log.d(TAG, "NFC Auto-Share enabled: $enabled")
    }

    private fun sendFile(file: File, socket: java.net.Socket) {
        try {
            val fileInputStream = FileInputStream(file)
            val outputStream = socket.getOutputStream()
            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            fileInputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error sending file: ${e.message}")
        }
    }

    private fun receiveFile(inputStream: java.io.InputStream, outputFile: File) {
        try {
            val fileOutputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                fileOutputStream.write(buffer, 0, bytesRead)
            }

            fileOutputStream.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error receiving file: ${e.message}")
        }
    }
}
