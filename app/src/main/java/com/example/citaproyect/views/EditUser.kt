package com.example.citaproyect.views

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.citaproyect.R
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.icons.filled.Edit
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.citaproyect.ApiService
import com.example.citaproyect.AppDatabase
import com.example.citaproyect.Usuario
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.example.citaproyect.views.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditUser(navController: NavController, usuarioId: String?) {
    var isConnected by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Inicializar la base de datos Room
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build() }
    val serviceDao = remember { db.ServiceDao() }

    // Inicializar Retrofit
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // URL de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService = remember { retrofit.create(ApiService::class.java) }

    // Control de la red
    NetworkStatusListener { networkAvailable ->
        isConnected = networkAvailable
    }

    // Carga la información del usuario si está disponible
    usuarioId?.let {
        LaunchedEffect(usuarioId) {
            val usuario = serviceDao.getUsuarioById(it.toInt())
            name = usuario?.nombre ?: ""
            description = usuario?.descripcion ?: ""
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Editar Perfil", fontSize = 35.sp, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Editar Nombre:", fontSize = 25.sp, color = Color.White)
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(8.dp)
                )

                Text("Editar Descripción:", fontSize = 25.sp, color = Color.White)
                BasicTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && description.isNotEmpty()) {
                            if (isConnected) {
                                // Si hay conexión a internet, procede con la actualización
                                isLoading = true
                                coroutineScope.launch {
                                    try {
                                        val usuario = Usuario(
                                            IdUsuario = usuarioId?.toInt() ?: 0,
                                            nombre = name,
                                            descripcion = description,
                                            email = "",  // Si lo necesitas, lo puedes rellenar aquí
                                            password = "" // Lo mismo con el password
                                        )
                                        val response = apiService.updateUser(usuarioId ?: "", usuario)
                                        if (response.isSuccessful) {
                                            // Sincronizamos con la base de datos local
                                            serviceDao.updateUsuario(usuario)
                                            Toast.makeText(context, "Datos actualizados en la API y localmente", Toast.LENGTH_SHORT).show()
                                            navController.navigate("User/$usuarioId")
                                        } else {
                                            Toast.makeText(context, "Error al actualizar en la API", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Log.e("EditUser", "Error de red: ${e.message}")
                                        Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            } else {
                                // Si no hay conexión, mostrar un mensaje de error
                                Toast.makeText(context, "Es necesario tener internet para editar", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.jellybean)),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(text = "Guardar Cambios", color = Color.White, fontSize = 20.sp)
                }


                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun NetworkStatusListener(onNetworkAvailable: (Boolean) -> Unit) {
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            onNetworkAvailable(true) // Hay conexión
        }

        override fun onLost(network: Network) {
            onNetworkAvailable(false) // Se perdió la conexión
        }
    }

    DisposableEffect(context) {
        connectivityManager.registerDefaultNetworkCallback(callback)
        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}
