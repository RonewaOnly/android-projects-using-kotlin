package com.example.folderreading

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import java.io.File


fun SelectFolderDialog(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    checkPermission: () -> Unit,
    onFolderSelected: (File) -> Unit
) {
    val activityLauncher = context as Activity

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    val dialog = AlertDialog.Builder(context)
        .setTitle("Select Folder")
        .setMessage("Please select a folder.")
        .setPositiveButton("Select") { _, _ ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                activityLauncher.startActivityForResult(intent, REQUEST_CODE_FOLDER_SELECTION)
            } else {
                checkPermission()
            }
        }
        .setNegativeButton("Cancel", null)
        .create()

    dialog.show()
}

const val REQUEST_CODE_FOLDER_SELECTION = 123