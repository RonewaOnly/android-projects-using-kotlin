package com.example.login_registerpages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login_registerpages.ui.theme.Login_registerpagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navConroller = rememberNavController()

            NavHost(navController = navConroller, startDestination = "LoginPage") {
                composable("RegistrationPage"){
                    RegistrationPage(navConroller = navConroller)
                }
                composable("LoginPage"){
                    LoginPage(navConroller = navConroller)
                }
                composable("home"){
                    Homepage()
                }
            }
        }
    }
}
@Composable
fun LoginPage(navConroller: NavController){
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)
        .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome User!", modifier = Modifier.padding(15.dp), fontSize = 25.sp, fontFamily = FontFamily.SansSerif, color = Color.Yellow )
        OutlinedTextField(value = username.value, onValueChange = {username.value = it }, label = { Text(text = "Enter username: ")})
        OutlinedTextField(value = password.value, onValueChange = {password.value = it }, label = { Text(text = "Enter password: ")})
        OutlinedButton(onClick = {
            navConroller.navigate("RegistrationPage")
        }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun RegistrationPage(navConroller: NavController){
    Text(text = "We at the register page")
    Button(onClick = { navConroller.navigate("LoginPage")}) {
        Text(text = "Back to Login")
    }
}
@Composable
fun Homepage(){

}

@Preview
@Composable
fun preview(){
    val navConroller = rememberNavController()

    NavHost(navController = navConroller, startDestination = "LoginPage") {
        composable("RegistrationPage"){
            RegistrationPage(navConroller = navConroller)
        }
        composable("LoginPage"){
            LoginPage(navConroller = navConroller)
        }
        composable("home"){
            Homepage()
        }
    }
}