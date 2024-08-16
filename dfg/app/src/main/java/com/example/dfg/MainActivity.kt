package com.example.dfg

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dfg.ui.theme.DfgTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val context = LocalContext.current
    var internalFiles by remember { mutableStateOf<List<File>>(emptyList()) }
    var externalFiles by remember { mutableStateOf<List<File>>(emptyList()) }

    Column {
        Button(onClick = {
            internalFiles = getFilesFromInternalStorage(context)
        }) {
            Text("Read Internal Storage")
        }
        LazyColumn {
            items(internalFiles) { file ->
                Text(text = file.name)
            }
        }

        Button(onClick = {
            if (checkExternalStoragePermission(context)) {
                externalFiles = getFilesFromExternalStorage()
            } else {
                requestExternalStoragePermission(context)
            }
        }) {
            Text("Read External Storage")
        }
        LazyColumn {
            items(externalFiles) { file ->
                Text(text = file.name)
            }
        }
    }
}

fun getFilesFromInternalStorage(context: Context): List<File> {
    val internalDir = context.filesDir
    return internalDir.listFiles()?.toList() ?: emptyList()
}

fun checkExternalStoragePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestExternalStoragePermission(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
        REQUEST_EXTERNAL_STORAGE_PERMISSION
    )
}

fun getFilesFromExternalStorage(): List<File> {
    val externalDir = Environment.getExternalStorageDirectory()
    return externalDir.listFiles()?.toList() ?: emptyList()
}

// Define request code for external storage permission
const val REQUEST_EXTERNAL_STORAGE_PERMISSION = 123