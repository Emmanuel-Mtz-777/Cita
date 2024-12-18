package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.citaproyect.ApiService
import com.example.citaproyect.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import com.example.citaproyect.R
import com.example.citaproyect.views.utils.isNetworkAvailable



@Composable
fun LoginSesion(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var usuarioId by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Configurar Retrofit
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Cambia esta URL según tu servidor de Node.js
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService = remember { retrofit.create(ApiService::class.java) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.black),
                        colorResource(id = R.color.black),
                        colorResource(id = R.color.darkMidnightBlue),
                        colorResource(id = R.color.richBlack),
                        colorResource(id = R.color.prussianBlue)
                    )
                )
            ),
    ) {
        IconButton(
            onClick = {
                navController.navigate("Login")
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Regresar",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (usuarioId.isNotEmpty()) {
                Text(
                    text = "¡Bienvenido! Tu ID de usuario es: $usuarioId",
                    color = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = {
                    if (!isNetworkAvailable(context)) {
                        Toast.makeText(context, "Por favor, conecta a internet", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = apiService.obtenerUsuarios()
                        if (response.isSuccessful) {
                            val usuarios = response.body() ?: emptyList()
                            val usuario = usuarios.find { usuario ->
                                usuario.email == email && usuario.password == password
                            }
                            withContext(Dispatchers.Main) {
                                if (usuario != null) {
                                    val usuarioId = usuario.IdUsuario.toString()
                                    navController.navigate("menu/$usuarioId")
                                } else {
                                    errorMessage = "Lo siento, no tienes cuenta registrada."
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                errorMessage = "Error al obtener los usuarios."
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Inicia Sesión", fontSize = 18.sp)
            }
        }
    }
}