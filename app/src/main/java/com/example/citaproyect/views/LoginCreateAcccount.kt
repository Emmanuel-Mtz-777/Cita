package com.example.citaproyect.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.room.Room
import com.example.citaproyect.ApiService
import com.example.citaproyect.AppDatabase
import com.example.citaproyect.R
import com.example.citaproyect.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.citaproyect.views.utils.isNetworkAvailable


@Composable
fun LoginCreateAcccount(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Inicializar la base de datos y el DAO solo una vez, no cada vez que la UI se recompondrá.
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build() }
    val serviceDao = remember { db.ServiceDao() } // Obtener el ServiceDao

    // Configurar Retrofit también solo una vez
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
                        colorResource (id = R.color.darkMidnightBlue),
                        colorResource(id = R.color.richBlack),
                        colorResource(id = R.color.prussianBlue)
                    )
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState), // Habilitar scroll vertical
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo de la aplicación
            Image(
                painter = painterResource(id = R.drawable.logo2), // Asegúrate de tener logo.png en el directorio drawable
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp) // Tamaño del logo
                    .padding(bottom = 16.dp)
            )

            // Texto "Bienvenido"
            Text(
                text = "Bienvenido",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de entrada para el nombre
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    imeAction = ImeAction.Done
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

            // Botón para crear cuenta
            Button(
                onClick = {
                    // Verificar conexión a internet
                    if (!isNetworkAvailable(context)) {
                        Toast.makeText(context, "Por favor, conecta a internet", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true // Mostrar el indicador de carga
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                // Obtener el último usuario de la base de datos local
                                val lastUsuario = serviceDao.getLastUsuario()
                                val newId = (lastUsuario?.IdUsuario ?: 0) + 1 // Si no hay registros, el ID será 1

                                // Crear un nuevo usuario con el nuevo ID
                                val usuario = Usuario(IdUsuario = newId, nombre = nombre, email = email, password = password)

                                // Insertar el usuario en la base de datos local
                                serviceDao.insertUsuario(usuario)
                                Log.d("InsertUser", "Usuario insertado: $usuario")

                                // Insertar el usuario en la base de datos de Node.js
                                val response: Response<Usuario> = apiService.insertUsuario(usuario)
                                if (response.isSuccessful) {
                                    // Si la inserción en Node.js es exitosa
                                    withContext(Dispatchers.Main) {
                                        isLoading = false // Detener el indicador de carga
                                        Toast.makeText(context, "Usuario insertado exitosamente", Toast.LENGTH_SHORT).show()
                                        navController.navigate("Login") // Navegar a la pantalla de login
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        isLoading = false
                                        Toast.makeText(context, "Error al insertar el usuario en el servidor", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    e.printStackTrace()
                                    Toast.makeText(context, "Error al insertar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f) // El botón ocupa el 50% del ancho de la pantalla
                    .padding(top = 16.dp),
                enabled = !isLoading // Deshabilitar el botón mientras se procesa
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(text = "Crear Cuenta", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
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
    }
}