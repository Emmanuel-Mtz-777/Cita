package com.example.citaproyect.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.citaproyect.ApiService
import com.example.citaproyect.AppDatabase
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.citaproyect.views.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback


import retrofit2.Call

import retrofit2.Response


@Composable
fun User(navController: NavController, usuarioId: String?) {
    Log.d("User", "usuarioId recibido: $usuarioId")
    val context = LocalContext.current
    val selectedItem = remember { mutableStateOf(4) }

    // Inicializar la base de datos y el DAO solo una vez
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build() }
    val serviceDao = remember { db.ServiceDao() }

    // Configurar Retrofit
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Cambia esta URL según tu servidor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService = remember { retrofit.create(ApiService::class.java) }

    // Estado para manejar el nombre del usuario, descripción y errores
    var userName by remember { mutableStateOf("Cargando...") }
    var userDescription by remember { mutableStateOf("Cargando descripción...") }
    var userCareer by remember { mutableStateOf("Cargando carrera...") }
    var userSemester by remember { mutableStateOf("Cargando semestre...") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Verificar conexión y obtener datos
    LaunchedEffect(usuarioId) {
        if (usuarioId != null) {
            try {
                // Verificar si hay acceso a internet
                val isConnected = isNetworkAvailable(context)

                if (isConnected) {
                    // Intentar obtener los datos desde la API si hay conexión
                    val response = apiService.obtenerUsuarioPorId(usuarioId)
                    if (response.isSuccessful) {
                        userName = response.body()?.nombre ?: "Usuario no encontrado"
                        userDescription = response.body()?.descripcion ?: "Sin Descripcion"
                        userCareer = response.body()?.email ?: "Sin Email"
                        userSemester = response.body()?.foto ?: "Sin Foto"
                    } else {
                        errorMessage = "Error al obtener datos de la API"
                        throw Exception("Error al obtener datos de la API")
                    }
                } else {
                    // Si no hay acceso a internet, buscar en la base de datos local
                    val localUser = serviceDao.getUsuarioById(usuarioId.toInt())
                    if (localUser != null) {
                        userName = localUser.nombre
                        userDescription = localUser.descripcion ?: "Sin Descripcion"
                        userCareer = localUser.email ?: "Sin Email"
                        userSemester = localUser.foto ?: "Sin foto"
                    } else {
                        userName = "Usuario no encontrado en la base local"
                        userDescription = "Descripción no disponible"
                        errorMessage = "No hay conexión a Internet y el usuario no está en la base local"
                    }
                }
            } catch (e: Exception) {
                // En caso de error, mostrar un mensaje adecuado
                if (errorMessage == null) {
                    errorMessage = "Error al cargar los datos"
                }
                // Intentar buscar en la base de datos local si ocurre un error
                val localUser = serviceDao.getUsuarioById(usuarioId.toInt())
                userName = localUser?.nombre ?: "Usuario no encontrado en la base local"
                userDescription = localUser?.descripcion ?: "Sin Descripcion"
            }
        } else {
            userName = "Usuario desconocido"
            userDescription = "Descripción no disponible"
        }
    }

    // Mostrar el Toast si hay un error
    if (errorMessage != null) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    // El resto de tu código para mostrar la UI
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = colorResource(id = R.color.darkMidnightBlue)) {
                val items = listOf(
                    NavigationItem("Home", Icons.Filled.Home),
                    NavigationItem("Groups", Icons.Filled.Search),
                    NavigationItem("Chats", Icons.Filled.Person),
                    NavigationItem("Events", Icons.Filled.Settings),
                    NavigationItem("User", Icons.Filled.Info)
                )

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem.value == index,
                        onClick = {
                            selectedItem.value = index
                            val route = when (item.label) {
                                "Home" -> "Menu/$usuarioId"
                                "Groups" -> "Groups/$usuarioId"
                                "Chats" -> "Chats/$usuarioId"
                                "Events" -> "Events/$usuarioId"
                                "User" -> "User/$usuarioId"
                                else -> "Menu/$usuarioId"
                            }
                            navController.navigate(route)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selectedItem.value == index) colorResource(id = R.color.jellybean) else Color.White
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                color = if (selectedItem.value == index) Color.White else Color.White
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.darkMidnightBlue),
                            colorResource(id = R.color.richBlack),
                            colorResource(id = R.color.prussianBlue)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen de perfil
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.auron),
                        contentDescription = "Imagen de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // Nombre de usuario
                Text(
                    text = userName,
                    fontSize = 22.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // ID del usuario
                Text(
                    text = "ID: $usuarioId",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )


                // Información adicional (Email, Foto)
                ProfileInfoField(label = "Descripcion:", info = userDescription)
                ProfileInfoField(label = "Email:", info = userCareer)


                // Botón para editar perfil
                Button(
                    onClick = {
                        navController.navigate("EditUser/$usuarioId") // Ruta de edición
                    },
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text("Editar Perfil")
                }
                Button(
                    onClick = {
                        navController.navigate("Login") // Ruta para cerrar sesión y redirigir a Login
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Cerrar Sesión")
                }
                // Botón para eliminar cuenta
                Button(
                    onClick = {
                        if (usuarioId != null) {
                            // Llamar a la API para eliminar el usuario
                            apiService.eliminarUsuario(usuarioId).enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        // Eliminar de la base de datos local dentro de una corutina
                                        CoroutineScope(Dispatchers.IO).launch {
                                            serviceDao.deleteUserById(usuarioId.toInt()) // Llamada suspendida
                                        }
                                        // Redirigir a la pantalla de login
                                        navController.navigate("Login")
                                    } else {
                                        Toast.makeText(context, "Error al eliminar usuario desde la API", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Eliminar Cuenta")
                }


            }
        }
    }
}

@Composable
fun ProfileInfoField(label: String, info: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color.White, fontSize = 20.sp)
        Text(
            text = info,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(Color.White.copy(alpha = 0.1f))
                .padding(8.dp),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}