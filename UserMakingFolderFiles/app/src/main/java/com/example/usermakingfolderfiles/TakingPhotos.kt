package com.example.usermakingfolderfiles

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


class TakingPhotos {
    @Composable
    fun imageCaptureFromCamera(){
        val context = LocalContext.current
        val file = context.createImageFile()
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            context.packageName +".provider",file
        )

        var captureImageuri by remember{
            mutableStateOf<Uri>(Uri.EMPTY)
        }

        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                captureImageuri = uri
            }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if(it){
                Toast.makeText(context,"Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            }else{
                Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(onClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                    cameraLauncher.launch(uri)
                }
                else{
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Text(text = "Capture Image")

            }
        }

        if(captureImageuri.path?.isNotEmpty() == true){
            Image(
                modifier = Modifier.padding(16.dp ,8.dp),
                painter = rememberAsyncImagePainter(captureImageuri),
                contentDescription = null
            )
        }else{
            Image(
                modifier = Modifier.padding(16.dp ,8.dp),
                painter = painterResource(id = R.drawable.icon_image),
                contentDescription = null
            )
        }

    }

    @Composable
    fun Context.createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        val imageFileName = "JPEG_"+timestamp + "_"
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            externalCacheDir
        )

        return image
    }
}