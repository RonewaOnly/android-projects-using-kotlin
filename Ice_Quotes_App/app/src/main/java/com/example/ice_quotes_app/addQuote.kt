package com.example.ice_quotes_app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ice_quotes_app.data.Quote
import com.google.firebase.Firebase
import com.google.firebase.database.database


@Preview(showBackground = true)
@Composable
fun AddQuotes(){
    val context = LocalContext.current
    var quotename by remember {
        mutableStateOf(TextFieldValue())
    }
    var quotedescription by remember {
        mutableStateOf(TextFieldValue())
    }
    var quoteauthor by remember {
        mutableStateOf(TextFieldValue())
    }
    var isClicked by remember {
        mutableStateOf(false)
    }

    val quoteList = remember { mutableStateListOf<Quote>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.onTertiary)

    ) {
        OutlinedTextField(
            value = quotename,
            onValueChange = { quotename = it },
            label = { Text(text = "Enter quote title: ") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 14.sp),
        )
        OutlinedTextField(
            value = quotedescription,
            onValueChange = { quotedescription = it },
            label = { Text(text = "Enter the quote : ") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 14.sp),
            minLines = 6,
            maxLines = 30
        )
        OutlinedTextField(
            value = quoteauthor,
            onValueChange = { quoteauthor = it },
            label = { Text(text = "Enter quote author: ") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 14.sp)
        )

        OutlinedButton(
            onClick = {
                if (quotename.text =="" || quotedescription.text =="" || quoteauthor.text =="") {
                    Toast.makeText(
                        context,
                        "Can't save empty values please fill in the missing values",
                        Toast.LENGTH_SHORT
                    ).show();
                }else{
                    isClicked = true;
                    val newQuote = Quote(
                        quotename.text,
                        quotedescription.text,
                        quoteauthor.text
                    )
                    quoteList.add(newQuote)
                    quotename = TextFieldValue()
                    quotedescription = TextFieldValue()
                    quoteauthor = TextFieldValue()

                }
            },
            modifier = Modifier
                .background(Color.Yellow)
                .padding(20.dp)
                .border(0.dp, Color.Transparent),

            ) {
            Text(
                text = "Add Quote...",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                fontSize = 20.sp


            )
        }
        if(isClicked){
            ViewQuotes(lists = quoteList)

            WritingToDatabase(values = quoteList)
        }


    }
}
@Composable
fun ViewQuotes(lists: List<Quote>){

    LazyColumn {
        items(lists) {quote ->

            Cards(
                name = quote.quoteName,
                description = quote.quoteDescription,
                author = quote.quoteAuthor
            )
        }
    }

}

@Composable
fun Cards(name:String,description:String,author:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),

        ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(14.dp))
        Text(
            text = description,
            textAlign = TextAlign.Start,
            modifier= Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.3f)
        )
        Spacer(modifier = Modifier.padding(14.dp))
        Text(
            text ="~ $author" ,
            textAlign = TextAlign.End,
            color = Color.DarkGray,
            modifier= Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun WritingToDatabase(values:List<Quote>){
    val database = Firebase.database
    val value1 = database.getReference("quoteName")
    val value2 = database.getReference("quoteDescription")
    val value3 = database.getReference("quoteAuthor")
    val context = LocalContext.current

    LazyColumn {
        items(values) {quote ->
            if (quote.quoteName == "" || quote.quoteDescription == "" || quote.quoteAuthor == ""){
                Toast.makeText(context,"Can't save empty values",Toast.LENGTH_SHORT).show()

            }else {
                value1.setValue(quote.quoteName)
                value2.setValue(quote.quoteName)
                value3.setValue(quote.quoteName)
            }
        }
    }

}