package com.example.ice3_loginregister

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ice3_loginregister.ui.theme.ICE3_LoginRegisterTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseApp.initializeApp(this)
        setContent {

            ICE3_LoginRegisterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*val context = LocalContext.current

                    readDataFromFirestore(context)*/

                    Login()
                }
            }
        }
    }
}

@Composable
fun Login() {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var openRegistartionForm by remember { mutableStateOf(false) }
    var openForgetForm by remember { mutableStateOf(false) }

    var count = 0;


    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Enter Username") },
            placeholder = { Text(text = "e.g tim10192") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true,
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter password") },
            placeholder = { Text(text = "") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true
        )

        Button(
            onClick = {
                      if (username.isNotEmpty() || password.isNotEmpty()){
                          readDataFromFirestore(context, username, password)
                      }else{
                          Toast.makeText(context,"Please Fill in all values",Toast.LENGTH_SHORT).show()
                      }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
        ) {
            Text(text = "Login")
        }

        OutlinedButton(
            onClick = {
                openForgetForm = true;
                ++count
                Toast.makeText(context,"${count}",Toast.LENGTH_SHORT).show()
                      },
            border = BorderStroke(width = 2.dp, color = Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Forget Password?",
            )
        }

        var showDialog by remember { mutableStateOf(false) }

        Button(
            onClick = { openRegistartionForm = true;
                ++count

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors( Color.LightGray)
        ) {
            Text(text = "Create a Account", color = Color.Black)
        }

        if (openRegistartionForm){
                Registration(openRegistartionForm,count)
        }
        else if (openForgetForm){
                ForgetPassword(openForgetForm,count)
            //ForgetPassword()
        }
    }
}

@Composable
fun Registration(openForm:Boolean,count:Int) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    if (openForm){

        if(count == 2){
            Toast.makeText(context,"Closing form",Toast.LENGTH_SHORT).show()
        }else {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Enter Username") },
                    placeholder = { Text(text = "e.g tim18") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Enter Password") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Confirm Password") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )

                Button(
                    onClick = {
                              if (password.equals(confirmPassword)){
                                  writeDataToFirestore(context,username,password)
                              }else{
                                  Toast.makeText(context,"Passwords do not matchs",Toast.LENGTH_SHORT).show()
                              }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp),
                    colors = ButtonDefaults.buttonColors(Color.Cyan)
                ) {
                    Text(text = "Register")
                }
            }
        }
    }

}

@Composable
fun ForgetPassword(openForm:Boolean,count:Int) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isHidden = remember { mutableStateOf(false) }


    if (openForm){
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (username.isNotEmpty()) {
                        Toast.makeText(context, "Found ", Toast.LENGTH_SHORT).show()
                        isHidden.value = !isHidden.value
                    } else {
                        Toast.makeText(context, "Not Found ", Toast.LENGTH_SHORT).show()
                    }
                },
                label = { Text(text = "Confirm username") },
            )
        }

        if (isHidden.value) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "New Password") },
                placeholder = { Text(text = "new password") },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()

            )
        }
    }

    if(count == 2){
        Toast.makeText(context,"Closing form",Toast.LENGTH_SHORT).show()
    }

}
// Write data to Firestore

fun writeDataToFirestore(context:Context, username:String,password:String) {
    val db = Firebase.firestore
    val data = hashMapOf(
        "username" to username,
        "password" to password,
    )

    // Add a new document with a generated ID
    db.collection("users")
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            Toast.makeText(context,"User add into the System",Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}

// Read data from Firestore

fun readDataFromFirestore(context:Context,username:String,password:String) {
    val db = Firebase.firestore

    db.collection("users")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")

                if(username.equals(document.data.get("username")) && password.equals(document.data.get("password"))){
                    Toast.makeText(context,"User is found",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"User is Not found",Toast.LENGTH_SHORT).show()
                }
                //Toast.makeText(context,"${document.id} => ${document.data}",Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {


        Login()


        //Registration()

}
