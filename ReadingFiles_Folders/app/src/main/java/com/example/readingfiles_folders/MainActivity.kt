package com.example.readingfiles_folders
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted, proceed with file creation
                createFile()
            } else {
                // Permission is not granted, show a toast message
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    App()
                }
            }
        }
    }

    private fun checkPermissionAndCreateFile() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted, proceed with file creation
                createFile()
            }
            else -> {
                // Request permission to write to external storage
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check and request permission when the activity starts
        checkPermissionAndCreateFile()
    }

    private fun createFile() {
        setContent {
            CreateFileContent()
        }
    }
}

@Composable
fun App() {
    // UI of the app goes here
    Text("Create File with Chosen Extension Type")
    CreateFileContent()
}

@Composable
fun CreateFileContent() {
    var fileName by remember { mutableStateOf("") }
    var fileExtension by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = fileName,
            onValueChange = { fileName = it },
            label = { Text("File Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = fileExtension,
            onValueChange = { fileExtension = it },
            label = { Text("File Extension") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { createFile(context, fileName, fileExtension) },
            enabled = fileName.isNotBlank() && fileExtension.isNotBlank()
        ) {
            Text("Create File")
        }
    }
}
fun createFile(context: Context, fileName: String, fileExtension: String) {
    val directory = context.getExternalFilesDir(null)

    if (directory != null) {
        val file = File(directory, "$fileName.$fileExtension")

        if (!file.exists()) {
            try {
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write("done Can ".toByteArray())
                fileOutputStream.close()
                Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error creating file: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "File already exists", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Failed to access external storage", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Surface {
            App()
        }
    }
}
