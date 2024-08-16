package com.example.workingwithokhttp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding


import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workingwithokhttp.ui.theme.WorkingWithOkHttpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //UserDatabase()

            MyApp()
        }
    }
}
@Composable
fun MyApp(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {
        composable("login"){
            LoginScreen()
        }
        composable("register"){
            RegisterScreen()
        }
    }
}

/*@Composable
fun UserDatabase(){
    var user by remember {
        mutableStateOf<List<User>>(emptyList())
    }
    var firstname by remember {
        mutableStateOf("")
    }
    var lastname by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        getCustomers(
            onResult = {fetchedUser -> user = fetchedUser},
            onError = {e -> e.printStackTrace()}
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(user.size){ index ->
                Text(text = " User: ${user[index].firstName},${user[index].lastName}")

            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = firstname,
            onValueChange = {firstname = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(firstname.isEmpty()) Text(text = "Enter firstname: ")
                else innerTextField()
            }
        )
        BasicTextField(
            value = lastname,
            onValueChange = {lastname = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(lastname.isEmpty()) Text(text = "Enter lastname: ")
                else innerTextField()
            }
        )
        BasicTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(email.isEmpty()) Text(text = "Enter email: ")
                else innerTextField()
            }
        )
        BasicTextField(
            value = phone,
            onValueChange = {phone = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(phone.isEmpty()) Text(text = "Enter cell number: ")
                else innerTextField()
            }
        )
        BasicTextField(
            value = address,
            onValueChange = {address = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(address.isEmpty()) Text(text = "Enter address: ")
                else innerTextField()
            }
        )
        BasicTextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = {innerTextField ->
                if(password.isEmpty()) Text(text = "Enter password: ")
                else innerTextField()
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            setCustomers(
                firstname,
                lastname,
                email,
                phone,
                address,
                password
                , onSuccess = {
                    getCustomers(
                        onResult = {
                            fetchedUsers -> user = fetchedUsers
                        },
                        onError = {
                            e-> e.printStackTrace()
                        }
                    )
                },
                onError = {

                }
            )
        },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
            Text(text = "Add user")
        }
    }
}
*/
@Composable
fun LoginScreen(authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.login(email, password, {
                message = it
            }, {
                message = it
            })
        }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(message)
    }
}

@Composable
fun RegisterScreen(authViewModel: AuthViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.register(firstName, lastName, email, phone, address, password, {
                message = it
            }, {
                message = it
            })
        }) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(message)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkingWithOkHttpTheme {
        //UserDatabase()
    }
}