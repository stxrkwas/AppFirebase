package com.example.appfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appfirebase.ui.theme.AppFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppFirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {

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
            Arrangement.Center
        ){
            Text(text = "App Firebase Firestore")
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
            Column(Modifier.fillMaxWidth(0.3f)){

                //Texto
                Text(text= "Nome:")
            }

            // Coluna 2
            Column(){

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
            Column(Modifier.fillMaxWidth(0.3f)){

                //Texto
                Text(text = "Telefone")
            }

            //Coluna 2
            Column(){
                TextField(
                    value = telefone,
                    onValueChange = {telefone = it}
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
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cadastrar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFirebaseTheme {
        App()
    }
}