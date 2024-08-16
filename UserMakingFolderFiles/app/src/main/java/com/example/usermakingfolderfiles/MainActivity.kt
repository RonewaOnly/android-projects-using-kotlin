package com.example.usermakingfolderfiles

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CameraAlt
import androidx.compose.material.icons.twotone.FileOpen
import androidx.compose.material.icons.twotone.Image
import androidx.compose.material.icons.twotone.Scanner
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.usermakingfolderfiles.ui.theme.UserMakingFolderFilesTheme
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserMakingFolderFilesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                        //MyApp()
                    MainScreen()
                }
            }
        }
    }
}
@Composable
fun MyApp() {
    Surface {
        CreateFolderScreen()
    }
}

@Composable
fun CreateFolderScreen() {
    val context = LocalContext.current
    var folderName by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = folderName,
            onValueChange = { folderName = it },
            label = { Text("Enter Folder Name") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                createFolder(context, folderName.text)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create Folder")
        }
    }
}

fun createFolder(context: Context, folderName: String) {
    val folder = File(context.getExternalFilesDir("/Downloads"), folderName)
    if (!folder.exists()) {
        folder.mkdir()
        Toast.makeText(context, "Folder created successfully", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Folder already exists", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun FilePicker(): List<Uri> {
    var selectedFiles by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val filePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                selectedFiles = listOf(it)
            }
        }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)

        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { filePickerLauncher.launch(null) }) {
            Text(text = "Pick Folder")
        }

        Spacer(modifier = Modifier.height(16.dp))

       /* if (selectedFiles.isNotEmpty()) {
            Text(text = "\"Selected Folder: ${selectedFiles.firstOrNull()}\"")
        }*/
    }

    return selectedFiles //this will be returning chosen folder to save items
}




@Composable
fun MainScreen(){
    var direction by remember { mutableStateOf<List<Uri>>(emptyList()) }
    //var selectedFiles by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    Scaffold (
        topBar = {
            //FilePicker()
             direction = FilePicker()

            if (direction.isEmpty()){
                Toast.makeText(context,"Empty",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"${direction}",Toast.LENGTH_SHORT).show()
            }
        }
    ){ paddingValues ->
        Spacer(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxWidth(0.7f))
        Column {
            Spacer(modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxWidth(0.7f))
            bar(direction = direction)
        }

        Spacer(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxWidth(0.7f))

    }
}
@Composable
fun DisplayFolderContents() {
    val context = LocalContext.current
    var folderContents by remember { mutableStateOf<List<File>>(emptyList()) }

    // ActivityResultLauncher to handle folder selection
    var  chooseFolder = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        uri?.let { path ->
            // Get the selected folder path
            val folderPath = getFolderPath(context, path)
            val paths : Uri = path
            // Read contents of the selected folder
            folderContents = getFolderContents(context,paths)
            Toast.makeText(context,"${folderPath}, ${folderContents }}",Toast.LENGTH_SHORT).show()

        }
    }

    Column {
        Button(onClick = {
            // Launch the folder picker
            chooseFolder.launch(null)
        }) {
            Text(text = "Choose Folder")
        }
        var pathes:Uri by remember {
            mutableStateOf<Uri>(Uri.EMPTY)
        }
        chooseFolder = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocumentTree()) {uri ->
            uri?.let { path ->
                // Get the selected folder path
                val folderPath = getFolderPath(context, path)
                val paths : Uri = path
                pathes = paths
                // Read contents of the selected folder
                folderContents = getFolderContents(context,pathes)
                Toast.makeText(context,"${folderPath}, $folderContents ",Toast.LENGTH_SHORT).show()
            }
        }

        // Display the folder contents using LazyColumn

        if(folderContents.isEmpty()){
            Text(text = "Folder is empty\n and the path: ${pathes}")
        }else{
            LazyColumn {
                items(folderContents) { fileName ->
                    // Display each file name as a list item

                    Toast.makeText(context,"${fileName.name}",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}

// Function to retrieve contents of a folder
fun getFolderContents(context: Context ,folderPath: Uri): List<File> {
     val folder = File(context.filesDir, "/Downloads")

    val folderContents = mutableListOf<File>()

    // Check if the folder exists and is a directory
    if (folder != null) {
        if (folder.exists() && folder.isDirectory) {
            // List all files and directories in the folder
            val files = folder.listFiles()?.asList()
            // Add the names of all files and directories to the list
            files?.forEach { file ->
                folderContents.add(file.parentFile)
            }
        }
    }

    return folderContents
}

// Function to get folder path from URI
fun getFolderPath(context: Context, uri: Uri): String {
    val document = DocumentsContract.getTreeDocumentId(uri)
    return DocumentsContract.getTreeDocumentId(uri).let { documentId ->
        if (document == "com.android.externalstorage.documents") {
            "file:///storage/${documentId.split(':')[1]}"
        } else {
            documentId
        }
    }
}

@Composable
fun bar(direction: List<Uri>){

    val context = LocalContext.current
    var folderContents by remember { mutableStateOf<List<String>>(emptyList()) }

    var imageClick by remember {
        mutableStateOf(false)
    }
    var cameraClick  by remember {
        mutableStateOf(false)
    }
    var fileClick  by remember {
        mutableStateOf(false)
    }
    var scannerClick  by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        IconButton(onClick = {
            imageClick = true
        }

        ) {
            Icon(
                imageVector = Icons.TwoTone.Image,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.size(70.dp),

            )
        }

        IconButton(onClick = { fileClick = true }
        ) {
            Icon(
                imageVector = Icons.TwoTone.FileOpen,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.size(70.dp)

            )
        }

        IconButton(onClick = { cameraClick = true }
        ) {
            Icon(
                imageVector = Icons.TwoTone.CameraAlt,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.size(70.dp)

            )
        }

        IconButton(onClick = { scannerClick = true }
        ) {
            Icon(
                imageVector = Icons.TwoTone.Scanner,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.size(70.dp)

            )
        }
    }
    if (imageClick)
    {
        GetImageFromGallery().GetImageFromGallery()

    }
    if(cameraClick){
        TakingPhotos().imageCaptureFromCamera()
    }
    if (scannerClick){
        ScannerDocument()
    }
    if (fileClick){
        //FolderContentsScreen(direction)
        DisplayFolderContents()

    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserMakingFolderFilesTheme{
        //MyApp()

        MainScreen()
    }
}