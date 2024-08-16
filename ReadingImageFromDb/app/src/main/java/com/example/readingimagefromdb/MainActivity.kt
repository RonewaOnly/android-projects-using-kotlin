package com.example.readingimagefromdb

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // This initializes Firebase
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun UploadFileScreen() {
    val storageRef: StorageReference = FirebaseStorage.getInstance().reference
    val databaseRef = FirebaseDatabase.getInstance().getReference("uploads")
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var uploadStatus by remember { mutableStateOf("") }
    var uploadedImageUrl by remember { mutableStateOf("") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { filePickerLauncher.launch("image/*") }) {
            Text(text = "Select Image to Upload")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedFileUri?.let { uri ->
            Button(onClick = {
                val fileName = uri.lastPathSegment ?: "unknown"
                val fileRef = storageRef.child("uploads/$fileName")
                val uploadTask = fileRef.putFile(uri)
                uploadTask.addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        val fileId = databaseRef.push().key // Generate a unique key for the file
                        fileId?.let {
                            val fileData = mapOf(
                                "fileName" to fileName,
                                "downloadUrl" to downloadUrl.toString()
                            )
                            databaseRef.child(it).setValue(fileData)
                            uploadStatus = "Upload successful! URL: $downloadUrl"
                            uploadedImageUrl = downloadUrl.toString()
                        }
                    }
                }.addOnFailureListener {
                    uploadStatus = "Upload failed: ${it.message}"
                }
            }) {
                Text(text = "Upload Image")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = uploadStatus, fontSize = 18.sp)

        if (uploadedImageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(model = uploadedImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DownloadFileScreen() {
    val databaseRef = FirebaseDatabase.getInstance().getReference("uploads")
    var downloadStatus by remember { mutableStateOf("") }
    var imageUrlList by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    LaunchedEffect(Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val urlList = mutableListOf<Pair<String, String>>()
                for (data in snapshot.children) {
                    val fileName = data.child("fileName").getValue(String::class.java)
                    val downloadUrl = data.child("downloadUrl").getValue(String::class.java)
                    if (fileName != null && downloadUrl != null) {
                        urlList.add(Pair(fileName, downloadUrl))
                    }
                }
                imageUrlList = urlList
            }

            override fun onCancelled(error: DatabaseError) {
                downloadStatus = "Failed to load data: ${error.message}"
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUrlList.isNotEmpty()) {
            Text(text = "Downloaded Images", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            imageUrlList.forEach { (fileName, downloadUrl) ->
                Text(text = "File: $fileName")
                Image(
                    painter = rememberAsyncImagePainter(model = downloadUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
            }
            downloadStatus = "Download successful!"
        } else {
            downloadStatus = "No images found."
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = downloadStatus, fontSize = 18.sp)
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("upload") { UploadFileScreen() }
        composable("download") { DownloadFileScreen() }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("upload") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Go to Upload Screen")
        }

        Button(
            onClick = { navController.navigate("download") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Go to Download Screen")
        }

    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}