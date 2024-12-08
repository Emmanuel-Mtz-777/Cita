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


@Composable
fun LoginSesion(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var usuarioId by remember { mutableStateOf("") } // Variable para almacenar el IdUsuario
    // Configurar Retrofit también solo una vez
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Cambia esta URL según tu servidor de Node.js
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Crear e inicializar el apiService usando Retrofit
    val apiService = remember { retrofit.create(ApiService::class.java) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // Fondo oscuro
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Campo de entrada para el email
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

            // Campo de entrada para la contraseña
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

            // Mostrar el IdUsuario si se encuentra
            if (usuarioId.isNotEmpty()) {
                Text(
                    text = "¡Bienvenido! Tu ID de usuario es: $usuarioId",
                    color = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Mensaje de error si no se encuentra el usuario
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
            val usuarioViewModel: UsuarioViewModel = viewModel()
            // Botón para iniciar sesión
            Button(
                onClick = {

                    // Llamada a la función para obtener los usuarios y verificar
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = apiService.obtenerUsuarios()
                        if (response.isSuccessful) {
                            val usuarios = response.body() ?: emptyList()
                            val usuario = usuarios.find { usuario ->
                                usuario.email == email && usuario.password == password
                            }
                            // Mover la navegación al hilo principal
                            withContext(Dispatchers.Main) {
                                if (usuario != null) {
                                    // Si el email y la contraseña son correctos, mostrar el ID y navegar al menú

                                    val usuarioId = usuario.IdUsuario.toString()
                                  // Almacenar el usuarioId en el ViewModel
                                    navController.navigate("menu/$usuarioId")
                                } else {
                                    // Si no se encuentra el usuario, mostrar mensaje de error
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Inicia Sesión", fontSize = 18.sp)
            }

        }
    }
}