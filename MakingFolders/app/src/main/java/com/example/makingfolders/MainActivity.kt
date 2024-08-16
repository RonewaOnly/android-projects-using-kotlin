package com.example.makingfolders

import android.app.Activity
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.makingfolders.ui.theme.MakingFoldersTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateFolderScreen()
        }
    }
}
@Composable
fun CreateFolderScreen() {
    var folderName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = folderName,
            onValueChange = { folderName = it },
            label = { Text("Folder Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { createFolder(context, folderName) }) {
            Text("Create Folder")
        }
    }
}

fun createFolder(context: Context, folderName: String) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Permission not granted, request it
        val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
    } else {
        // Permission granted, proceed with folder creation
        val folder = File(Environment.getExternalStorageDirectory(), folderName)
        if (!folder.exists()) {
            val created = folder.mkdirs()
            if (created) {
                // Folder created successfully
                Log.d("CreateFolder", "Folder created: $folder")
                // Optionally, you can show a toast or update UI to indicate success
                Toast.makeText(context, "Created Successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Folder creation failed
                Log.e("CreateFolder", "Failed to create folder: $folder")
                // Optionally, you can show a toast or update UI to indicate failure
                Toast.makeText(context, "Failed to create", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Folder already exists
            // Optionally, you can show a toast or update UI to indicate that
            Log.d("CreateFolder", "Folder already exists: $folder")
            Toast.makeText(context, "The Folder already exists", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview
@Composable
fun PreviewCreateFolderScreen() {
    CreateFolderScreen()
}