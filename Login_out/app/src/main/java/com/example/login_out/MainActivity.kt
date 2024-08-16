package com.example.login_out

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login_out.ui.theme.Login_outTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Landing()
        }
    }
}
@Preview
@Composable
fun Landing(){
    val rememberNav = rememberNavController();

    NavHost(navController = rememberNav, startDestination = "login") {
        composable("login"){
            Login(rememberNav)
        }
        composable("registartion"){
            Registartion(rememberNav)
        }
    }
}

@Composable
fun Login(navController: NavController){
    var input1 by
    remember {
        mutableStateOf("")
    }



  var input2 by remember {
      mutableStateOf("")
  }
    Column(modifier= Modifier.background(Color.Cyan).padding(10.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(value = input1, onValueChange = {input1 = it   }, placeholder = {
            Text(text = "Enter username")
        })
        OutlinedTextField(value = input2, onValueChange = {input2 = it   }, placeholder = {
            Text(text = "Enter password")
        })

        OutlinedButton(onClick = { navController.navigate("registartion") }) {
            Text(text = "Login")
        }
    }
}
@Composable
fun Registartion(navController: NavController){
    var input1 by remember {
        mutableStateOf("")
    }
    var input2 by remember {
        mutableStateOf("")
    }
    var input3 by remember {
        mutableStateOf("")
    }
    Column {

        OutlinedTextField(value = input1, onValueChange = {input1 = it}, placeholder = {
            Text(text = "Enter Name: ")
        })

        OutlinedTextField(value = input2, onValueChange = {input2 = it}, placeholder = {
            Text(text = "Enter username: ")
        })

        OutlinedTextField(value = input3, onValueChange = {input3 = it}, placeholder = {
            Text(text = "Enter password: ")
        })

        OutlinedButton(onClick = { navController.navigate("login") }) {
            Text(text = "Sign up")
        }

    }
}