package com.example.appfirebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appfirebase.ui.theme.AppFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppFirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(db)
                }
            }
        }
    }

    companion object {
        private const val TAG = "AppFirebase" // Define a custom tag for logging
    }
}

@Composable
fun App(db: FirebaseFirestore) {

    //Variável nome:
    var nome by remember {
        mutableStateOf("")
    }

    //Variável telefone
    var telefone by remember {
        mutableStateOf("")
    }

    //Coluna:
    Column (Modifier.fillMaxWidth()) {

        //Linha 1
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        //Linha 2
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.Center,
        ){
            Text(
                text = "App Firebase Firestore",
                style = MaterialTheme.typography.headlineMedium)
        }

        //Linha 3
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        //Linha 4
        Row(Modifier.fillMaxWidth()){

            //Coluna 1
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
                    .padding(25.dp)){

                //Texto
                Text(
                    text= "Nome:",
                    modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            // Coluna 2
            Column(
                Modifier.padding(5.dp)
            ){

                //Campo de texto
                TextField(
                    value = nome,

                    //Ao mudar o valor, ele é igual a 'nome'
                    onValueChange = {nome = it}
                )
            }
        }//Finalizando a linha 4

        //Linha 5
        Row(Modifier.fillMaxWidth()){

            //Coluna 1
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
                    .padding(25.dp)){

                //Texto
                Text(
                    text = "Telefone:",
                    modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            //Coluna 2
            Column(
                Modifier.padding(5.dp)
            ){
                TextField(
                    value = telefone,
                    onValueChange = {telefone = it},
                    modifier = Modifier.fillMaxWidth(0.92f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
        }//Finalizando a linha 5

        //Linha 6
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        //Linha 7
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.Center
        ){
            
            //Botão
            Button(
                onClick = {

                //Variável city
                val city = hashMapOf(
                    "nome" to nome,
                    "telefone" to telefone
                )

                db.collection("Clientes").document("PrimeiroCliente")
                    .set(city)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot succesfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }) {

                //Título do botão
                Text(text = "Cadastrar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFirebaseTheme {
        App(db = FirebaseFirestore.getInstance())
    }
}

