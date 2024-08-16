package com.example.scanning_documents

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.scanning_documents.ui.theme.Scanning_DocumentsTheme
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scanning_DocumentsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScannerDocument()
                }
            }
        }
    }
}

@Composable
fun ScannerDocument(){
    val options = GmsDocumentScannerOptions.Builder()
        .setGalleryImportAllowed(false)
        .setPageLimit(2)
        .setResultFormats(RESULT_FORMAT_JPEG,RESULT_FORMAT_PDF)
        .setScannerMode(SCANNER_MODE_FULL)
        .build()

    //Saving the uri for the image to display
    var scannedImageUri by remember {
        mutableStateOf(Uri.EMPTY)
    }
    //Initializes the docuement scanner
    val scanner = GmsDocumentScanning.getClient(options)

    //Scanner Launcher
    val scannerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {result ->
        if(result.resultCode == RESULT_OK){
            val scanningResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)

            //Getting the scanned page
            scanningResult?.pages?.let { pages ->
                for (page in pages){
                    scannedImageUri = pages.get(0).imageUri
                }
            }
        }
    }

    val activity = LocalContext.current as Activity

    //If in MainActivity simply use 'this@MainActivity'

    Column {
        AsyncImage(
            model = scannedImageUri, 
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Button(onClick = {
            scanner.getStartScanIntent(activity)
                .addOnSuccessListener { intentSender ->
                    scannerLauncher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                }
                .addOnFailureListener{
                    Log.e("error",it.message.toString())
                }
        }) {
            Text(text = "Start Scanning")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Scanning_DocumentsTheme {
        ScannerDocument()
    }
}