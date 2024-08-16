package com.example.authapp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.example.authapp.ui.theme.AuthAppTheme

object Auth0Config{
    const val CLIENT_ID = "YeY4E3rysSrHh0jkSXMo0C1F5laIXNwE"
    const val DOMAIN = "dev-jyotnd875b0ivq20.us.auth0.com"
}
class MainActivity : ComponentActivity() {
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        account = Auth0(Auth0Config.CLIENT_ID, Auth0Config.DOMAIN)

        setContent {
            MyApp(account = account)
        }
    }
}
@Composable
fun MyApp(account: Auth0) {
    MaterialTheme {
        val context = LocalContext.current
        val loginResult by remember { mutableStateOf<String?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data: Intent? = result.data
            if (result.resultCode == RESULT_OK && data != null) {
                WebAuthProvider.resume(data)
            }
        }

        LoginScreen(onLogin = {
            WebAuthProvider.login(account)
                .withScheme("demo")

        })

        loginResult?.let {
            // Display login result or handle accordingly
        }
    }
}
@Composable
fun LoginScreen(onLogin: () -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = onLogin) {
            Text("Login")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLogin = {})
}