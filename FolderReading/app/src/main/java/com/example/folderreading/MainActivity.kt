package com.example.folderreading

import android.app.Activity
import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.folderreading.ui.theme.FolderReadingTheme
import java.io.File

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, do nothing
            } else {
                // Permission denied, handle accordingly
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                requestPermissionLauncher = requestPermissionLauncher,
                checkPermission = this::checkPermission
            )
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted, do nothing
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}

    @Composable
    fun App(requestPermissionLauncher: ActivityResultLauncher<String>,
            checkPermission: () -> Unit) {

        val context = LocalContext.current
        var selectedFolder by remember { mutableStateOf<File?>(null) }
        val paths = selectedFolder?.path
        val filesInFolder by remember(selectedFolder) {
            derivedStateOf {
                selectedFolder?.listFiles()?.toList() ?: emptyList()

            }
        }

        if(paths.isNullOrBlank()){
            Toast.makeText(context, "null",Toast.LENGTH_SHORT).show()

        }
        Toast.makeText(context, "${paths.toString()}",Toast.LENGTH_SHORT).show()
        println(selectedFolder);



        Column {
            Button(onClick = {
                SelectFolderDialog(context, requestPermissionLauncher, checkPermission= checkPermission) { folder ->
                    selectedFolder = folder
                }
            }) {
                Text("Select Folder")
            }
            LazyColumn {
                items(filesInFolder) { file ->
                    Text(text = file.name)
                }
            }
            //selectedFolder?.let { FolderContent(folder = it.absoluteFile) }


        }
    }

@Composable
fun FolderContent(folder: File) {
    val context = LocalContext.current
    val filesInFolder by remember { mutableStateOf(folder.listFiles()?.toList() ?: emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Files in selected folder:")
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(filesInFolder) { file ->
                Text(text = file.name)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Implement action to select another folder */ }) {
            Text("Select Another Folder")
        }
    }
}
