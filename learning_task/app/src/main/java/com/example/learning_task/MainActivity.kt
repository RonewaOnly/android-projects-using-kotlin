package com.example.learning_task

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learning_task.ui.theme.Learning_taskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen()
        }
    }
}

@Composable
fun Login(){
    var username = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(Color.Cyan, shape = RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(value = username.value, onValueChange = {username.value = it}, placeholder = {
            Text(text = "Enter username")
        })

        TextField(value = password.value, onValueChange = {password.value = it },placeholder = {
            Text(text = "Enter password")
        })

        Button(onClick = {
            Toast.makeText(context,"Logged in",Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun Registartion(){

    Column(

        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary, shape = RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            OutlinedTextField(value = "", onValueChange = {})
        OutlinedTextField(value = "", onValueChange = {})
        OutlinedTextField(value = "", onValueChange = {}, visualTransformation = PasswordVisualTransformation())
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "register")
        }
    }
}
@Preview
@Composable
fun Screen(){
    val remember = rememberNavController()

    NavHost(navController = remember, startDestination = "Login" ) {
        composable("Login"){
            Login()
        }
        composable("Registration"){
            Registartion()
        }
    }
     //Login()
    Registartion()
}