package com.example.appfirebase

import android.content.ContentValues.TAG
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.appfirebase.ui.theme.AppFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore

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
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    AppFirebaseTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(db: FirebaseFirestore, modifier: Modifier = Modifier) {

    //Variável nome:
    var nome by remember {
        mutableStateOf("")
    }

    //Variável telefone
    var telefone by remember {
        mutableStateOf("")
    }

    val clientes = remember{
        mutableStateListOf<HashMap<String, String>>()
    }

    //Coluna:
    Column (Modifier.fillMaxWidth()) {

        //Linha 1 (vazia)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        //Linha 2
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "App Firebase Firestore")

        }

        //Linha 3 (vazia)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        // Caixa que contém a foto de perfil
        Box(Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){

        // Imagem
            Image(
                painter = painterResource(id = R.drawable.foto_perfil),
                contentDescription = "Maria Luiza Passo Silva",
                modifier = Modifier.size(200.dp)
                    .clip(CircleShape)
            )
        }

        //Linha 4 - campo de texto 'nome'
        Row(Modifier.fillMaxWidth()
            .padding(top = 15.dp)){

            //Coluna 1
            Column(
                Modifier
                    .fillMaxWidth(0.1f)
                    // .padding original = 25dp
                    .padding(15.dp)){
            }

            // Coluna 2
            Column(
                Modifier.padding(5.dp)
                    .fillMaxWidth(1.0f)
            ){

                //Campo de texto
                TextField(
                    placeholder = { Text(text = "Digite seu nome") },
                    label = { Text(text = "Nome") },
                    value = nome,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f),

                    //Ao mudar o valor, ele é igual a 'nome'
                    onValueChange = {nome = it}
                )
            }
        }//Finalizando a linha 4

        //Linha 5 - do campo de texto 'telefone'
        Row(Modifier.fillMaxWidth()
            .padding(top = 15.dp)){

            //Coluna 1
            Column(
                Modifier
                    .fillMaxWidth(0.1f)
                    .padding(5.dp)){
            }

            //Coluna 2
            Column(
                Modifier.padding(5.dp)
                    .fillMaxWidth(1.0f)
            ){
                TextField(
                    label = { Text(text = "Telefone") },
                    placeholder = { Text(text = "(00)00000-0000") },
                    value = telefone,
                    singleLine = true,
                    onValueChange = {telefone = it},
                    modifier = Modifier.fillMaxWidth(0.9f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
        }//Finalizando a linha 5

        //Linha 6 (vazia)
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

                //Variável pessoas - 1ª modificação do código
                val pessoas = hashMapOf(
                    "nome" to nome,
                    "telefone" to telefone
                )

                // 2ª modificação do código
                db.collection("Clientes").add(pessoas)
                    .addOnSuccessListener{ documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                    Log.d(TAG, "Último Cliente cadastrado: $clientes")

                    db.collection("Clientes")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                val lista = hashMapOf(
                                    "nome" to (document.getString("nome")?: "--"),
                                    "telefone" to (document.getString("telefone")?: "--")
                                )
                                clientes.add(lista)
                                Log.i(TAG, "Lista: $lista")
                            }
                        }

                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents.", exception)
                        }
                    }
                    // Fim da modificação do trecho

                    .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
            }) {

                //Título do botão
                Text(text = "Cadastrar")
            }
        }

        //Linha 8
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){}

        //Linha 10
        Row(
            Modifier.fillMaxWidth()
        ){
            Column(Modifier.fillMaxWidth()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()){
                    item{
                        //Linha 9 - Exibição dos dados cadastrados
                        Row(Modifier.fillMaxWidth()){
                            Column(Modifier.fillMaxWidth(0.5f)
                                .padding(10.dp)){
                                Text(text = "Nome: ")
                            }
                            Column(Modifier.fillMaxWidth(0.5f)
                                .padding(10.dp)
                            ){
                                Text(text = "Telefone: ")
                            }
                        }
                    }

                    items(clientes){ cliente ->
                        Row(modifier = Modifier.fillMaxWidth()){
                            Column(modifier = Modifier.weight(0.5f)){
                                Text(text = cliente["nome"] ?: "--")
                            }
                            Column(modifier = Modifier.weight(0.5f)){
                                Text(text = cliente["telefone"] ?: "--")
                            }
                        }
                    }
                }
            }


        }
    }
}


