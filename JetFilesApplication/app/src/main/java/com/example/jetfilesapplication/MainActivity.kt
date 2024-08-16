package com.example.jetfilesapplication

import android.content.Intent
import  android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jetfilesapplication.Result
import com.example.jetfilesapplication.ui.theme.JetFilesApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermissions()){
            renderComposeView()
        }else{
            requestPermission()
        }

    }
}

private fun renderComposeView(){
    val appContainer = (application as JetFilesApplication).container
    setContent {
        JetFilesApp(appContainer)
    }
}

private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    if (Environment.isExternalStorageManager()){
        renderComposeView()
    }else{
        showPermissionAllowDialog()
    }
}

private fun showPermissionAllowDialog(){
    DialogUtils.showAlertDialog(
        this,
        R.string.permission_required_title,
        R.string.permission_required_message,
        R.string.permission_required_button
    ){
        requestPermission()
    }
}

private fun requestPermission(){
    if(isAndroid11OrAbove()){
        try {
            //We first try to open our application's details screen in the Settings application via
            //package name.
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
            resultLauncher.launch(intent)
        }catch (e: Exception){
            //If for some reason, Settings screen can't locate the app in the list,
            //screen and let user find the app
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            resultLauncher.launch(intent)
        }
    }else{
        //For API level < 30, handle the permission in the normal way
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }
}

override fun onRequestPermissionsResult(requestCode: Int,permission: Array<out String>,grantResults: IntArray){
    super.onRequestPermissionsResult(requestCode,permission,grantResults)
    when(requestCode){
        STORAGE_PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty()){
            val writeStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            //Make sure both the permissions are given
            if (writeStoragePermission){
                renderComposeView()
            }else{
                showPermissionAllowDialog()
            }

        }
    }
}

private fun isAndroid11OrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

private fun hasPermissions():Boolean{
    //Check if the API level is >= 30
    return if (isAndroid11OrAbove()){
        Environment.isExternalStorageManager()
    }
    //if the API level is < 30, check the permissions in the traditional way.
    else{
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetFilesApplicationTheme {
    }
}