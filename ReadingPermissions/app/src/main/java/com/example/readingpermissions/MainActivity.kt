@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.readingpermissions

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.readingpermissions.ui.theme.ReadingPermissionsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingPermissionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                    CameraCaptureScreen()
                }
            }
        }
    }


    @Composable
    fun Greeting(modifier: Modifier = Modifier) {
        val context = LocalContext.current;

        val cameraPermissionState =
            rememberPermissionState(Manifest.permission.CAMERA)//it returns a PermissionState object that contains information about the permission status(granted,denied,or not requested yet);
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {

                } else {

                }

            }
        LaunchedEffect(cameraPermissionState) {
            if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

    }
    /*
@Composable
fun gettingImages(){
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember{
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if(it){
            Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }else{
            Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }
    Column (
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            val permissionCheckResult = ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                cameraLauncher.launch(uri)
            }else{
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text(text = "Capture Image From Camera")
        }
    }
    if (capturedImageUri.path?.isNotEmpty() == true){

            capturedImageUri.
    }
}
fun Context.createImageFile(): File {
    //Create an Image File name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_"+timeStamp+"_"
    val image = File.createTempFile(
        imageFileName,/*prefix*/
            ".jpg",/*suffix*/
        externalCacheDir//directory
    )
    return  image
}*/

    @Composable
    fun CameraCaptureScreen() {
        var capturedBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        val context = LocalContext.current

        val takePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                val photoFile = File(context.getExternalFilesDir(null), "photo.jpg")
                capturedBitmap = photoFile.readBitmap()
            }
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            if (capturedBitmap != null) {
                Image(
                    bitmap = capturedBitmap!!,
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                Button(
                    onClick = {
                        takePhotoLauncher.launch(null)
                    },
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text("Take Photo")
                }
            }
        }

    }

    private fun File.readBitmap(): ImageBitmap {
        return ImageBitmap(100,100)
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ReadingPermissionsTheme {
            Greeting()

        }
    }
}