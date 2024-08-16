package com.example.my_application

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.my_application.ui.theme.My_ApplicationTheme
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My_ApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}
// App entry point
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colorScheme.background) {
        // Call the composable function to allow user to choose folder and file
        ChooseFolderAndFile()
    }
}

// Composable function to allow user to choose folder and file
@Composable
fun ChooseFolderAndFile() {
    val context = LocalContext.current
    var folderUri by remember { mutableStateOf<Uri?>(null) }

    // ActivityResultLauncher to handle folder selection
    val chooseFolder = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        folderUri = uri
    }

    // ActivityResultLauncher to handle file selection
    val chooseFile = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { fileUri ->
        fileUri?.let { uri ->
            folderUri?.let { folderUri ->
                saveFileToFolder(context, uri, folderUri)
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Launch the folder picker
            chooseFolder.launch(null)
        }) {
            Text("Choose Folder")
        }

        Button(
            onClick = {
                // Check if folder is selected
                if (folderUri != null) {
                    // Launch the file picker
                    chooseFile.launch(arrayOf("*/*").toString())
                } else {
                    Toast.makeText(context, "Please choose a folder first.", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Choose File to Save")
        }
    }

}

// Function to save a file to the selected folder
private fun saveFileToFolder(context: Context, fileUri: Uri, folderUri: Uri) {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(fileUri)
    val folderDocumentUri: Uri = DocumentsContract.buildDocumentUriUsingTree(folderUri, DocumentsContract.getTreeDocumentId(folderUri))

    inputStream?.let { inputStream ->
        val file = File(folderDocumentUri.toString(), getFileName(contentResolver, fileUri))
        val outputStream: OutputStream = context.contentResolver.openOutputStream(fileUri)!!
        inputStream.copyTo(outputStream)
        outputStream.close()
        inputStream.close()
        Toast.makeText(context, "File saved to ${file.path}", Toast.LENGTH_SHORT).show()
    }
}

// Function to get the file name from its URI
private fun getFileName(contentResolver: ContentResolver, fileUri: Uri): String {
    var fileName = "unknown"
    contentResolver.query(fileUri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                fileName = cursor.getString(displayNameIndex)
            }
        }
    }
    return fileName
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My_ApplicationTheme {
    }
}