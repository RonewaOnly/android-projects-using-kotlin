package com.example.trysome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trysome.Pages.HomeScreen
import com.example.trysome.Pages.LoginScreen
import com.example.trysome.Pages.ProfileScreen
import com.example.trysome.ui.theme.TrySomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Profile : Screen("profile")
}

@Composable
fun NavBar(navController: NavController){
    NavigationBar {
        NavigationBarItem(
            selected = false,
            label = { Text(text = "Home")},

            onClick = {
                      navController.navigate("home")
            }, 
            icon = { 
                Icons.Filled.Home
            })
        NavigationBarItem(
            selected = false,
            label = { Text(text = "Login")},
            onClick = {
                navController.navigate("login")

            },
            icon = {
                Icons.Outlined.AccountCircle
            })
        NavigationBarItem(
            selected = false,
            label = { Text(text = "Profile")},

            onClick = {
                navController.navigate("Profile")
            },
            icon = {
                Icons.Filled.Person
            })
    }
}
@Composable
fun Base() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController = navController)}
    ) { paddingValues ->
        Spacer(modifier = Modifier.padding(paddingValues))
        NavHost(navController = navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) { LoginScreen() }
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Base()
}