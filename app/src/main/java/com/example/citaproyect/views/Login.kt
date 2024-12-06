package com.example.citaproyect.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.network.RetrofitClient
import com.example.citaproyect.tablas.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun Login(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Contexto adecuado para la base de datos
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darkMidnightBlue))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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

            // Botón para iniciar sesión
            Button(
                onClick = {
                    if (nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        // Validación y lógica de login
                        // Aquí puedes agregar la lógica para autenticar al usuario con nombre, email y contraseña
                        CoroutineScope(Dispatchers.IO).launch {
                            // Ejemplo de inserción (si es necesario)
                            // val usuario = Usuario(nombre = nombre, email = email, password = password)
                            // DatabaseProvider.getInstance(context).ServiceDao().insertUsuario(usuario)

                            // Mostrar mensaje de éxito
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                navController.navigate("Menu") // Navegar al menú o pantalla deseada
                            }
                        }
                    } else {
                        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Iniciar Sesión", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Otras opciones o mensajes
            Text(
                text = "¿No tienes cuenta? Regístrate",
                color = colorResource(id = R.color.carolinaBlue),
                fontSize = 18.sp,
                modifier = Modifier.clickable { /* Acción para registro */ }
            )
        }
    }
}
