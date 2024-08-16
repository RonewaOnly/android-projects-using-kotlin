package com.example.workingwithokhttp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class AuthViewModel  : ViewModel() {

    private  val client = OkHttpClient()

    fun login(email: String, password: String, onResult: (String)-> Unit,onError: (String)->Unit){
            val requestBody = FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .build()

        val request = Request.Builder()
            .url("http://www.rdmteam.wuaze.com/login.php")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                viewModelScope.launch { onError(e.message ?: "Unknown error") }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if(response.isSuccessful && responseBody != null){
                    val json = JSONObject(responseBody)
                    val message = json.getString("message")
                    viewModelScope.launch { onResult(message) }
                }else{
                    viewModelScope.launch { onError("Server error") }
                }
            }

        })
    }

    fun register(firstName: String, lastName: String, email: String, phone: String, address: String, password: String, onResult: (String) -> Unit, onError: (String) -> Unit) {
        val requestBody = FormBody.Builder()
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("email", email)
            .add("phone", phone)
            .add("address", address) // Fixed potential typo
            .add("password", password) // Fixed potential typo
            .build()

        val request = Request.Builder()
            .url("http://www.rdmteam.wuaze.com/register.php")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModelScope.launch { onError(e.message ?: "Unknown error") }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                // Log the response body for debugging
                Log.d("HTTP_RESPONSE", responseBody ?: "No response body")

                if (response.isSuccessful) {
                    try {
                        val json = responseBody?.let { JSONObject(it) }
                        val message = json?.getString("message")
                        viewModelScope.launch {
                            if (message != null) {
                                onResult(message)
                            }
                        }
                    } catch (e: JSONException) {
                        viewModelScope.launch { onError("Invalid JSON response") }
                    }
                } else {
                    viewModelScope.launch { onError("Server error: ${response.code}") }
                }
            }
        })
    }


}