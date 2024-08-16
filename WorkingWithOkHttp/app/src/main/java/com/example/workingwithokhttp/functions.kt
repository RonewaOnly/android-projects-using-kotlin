package com.example.workingwithokhttp

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

//OkHttp instance...
private  val instance = OkHttpClient()

//getting customers from the database

fun getCustomers(onResult: (List<User>)->Unit,onError: (Exception)->Unit){
    val request = Request.Builder()
        .url("http://www.rdmteam.wuaze.com/customers.php")
        .build()

    instance.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonResponse ->
                val users = parseJsonToItemList(jsonResponse)
                onResult(users)
            } ?: onError(IOException("Empty response"))
        }
    })
}

//Setting into the database
fun setCustomers(firstname: String,lastname: String, email:String,phone:String,ddress:String,password:String, onSuccess: () -> Unit, onError: (Exception) -> Unit){
    val formBody = FormBody.Builder()
        .add("firstName",firstname)
        .add("lastName",lastname)
        .add("email",email)
        .add("phone",phone)
        .add("ddress",ddress)
        .add("pwrd",password)
        .build()

    val request = Request.Builder()
        .url("http://www.rdmteam.wuaze.com/register.php")
        .post(formBody)
        .build()

    instance.newCall(request).enqueue(object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                onSuccess()
            } else {
                onError(IOException("Unexpected code $response"))
            }
        }
    })
}

fun parseJsonToItemList(jsonResponse: String): List<User>{
    val gson = Gson()
    return try {
        gson.fromJson(jsonResponse, Array<User>::class.java).toList()
    } catch (e: JsonSyntaxException) {
        // Handle the case where the response is not a JSON array
        println("Parsing error: ${e.message}")
        emptyList()
    }
}