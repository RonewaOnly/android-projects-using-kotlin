package com.example.viewmodelspractiing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodelspractiing.Logic.LoginState
import com.example.viewmodelspractiing.Logic.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()){
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(loginState){
            is LoginState.IDLE ->{
                LoginForm(onLoginClicked = { username, password ->
                    viewModel.login(username, password)
                })
            }
            is LoginState.LOADING ->{
                Text(text = "LOADING...")
            }
            is LoginState.SUCCESS ->{
                Text(text = "Successful")
                LoginForm(onLoginClicked = { username, password ->
                    viewModel.login(username, password)
                })
            }
            is LoginState.ERROR -> {
                LoginForm(onLoginClicked = { username, password ->
                    viewModel.login(username, password)
                })
                Text("Error: ${(loginState as LoginState.ERROR).message}")
            }
        }
    }
}

@Composable
fun LoginForm(onLoginClicked: (String,String) -> Unit){
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = username, onValueChange = {username = it}, label = { Text(text = "Username")})
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password")})
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { onLoginClicked(username, password) }) {
            Text(text = "Login")
        }
    }

}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}