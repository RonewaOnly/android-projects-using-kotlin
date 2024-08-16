package com.example.learning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learning.ui.theme.LearningTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
fun Login(navController: NavController){
    Column(
        modifier= Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = "", onValueChange ={} , placeholder = {
            Text(text = "Username")
        }  )
        OutlinedTextField(value = "", onValueChange ={}, placeholder = {
            Text(text = "Password")
        })

        Button(onClick = {

            navController.navigate("register")
        }) {
            Text(text = "Login")

        }
    }
}

@Composable
fun Registration(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        OutlinedTextField(value = "", onValueChange ={} )
        OutlinedTextField(value = "", onValueChange ={} )
        OutlinedTextField(value = "", onValueChange ={})


        Button(onClick = {
            navController.navigate("login")

        }) {
            Text(text = "Registration")

        }
    }



}
@Composable
@Preview
fun Screen() {
    val remember = rememberNavController();
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = remember, startDestination = "Login") {
            composable("login") {
                Login(remember)

            }
            composable("register") {
                Registration(remember)
            }
        }
    }
}
