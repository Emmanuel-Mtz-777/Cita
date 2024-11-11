package com.example.citaproyect

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Asegúrate de importar LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citaproyect.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar la conexión a internet al iniciar la actividad
        val isConnected = isNetworkAvailable()

        if (!isConnected) {
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_LONG).show()
        }

        setContent {
            ComposeMultiScreenApp(isConnected)
        }
    }

    // Función para verificar si hay acceso a Internet
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

@Composable
fun ComposeMultiScreenApp(initialConnectionState: Boolean) {
    var isConnected by remember { mutableStateOf(initialConnectionState) }

    // Monitorear cambios en la conexión de internet
    val connectivityManager = LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val context = LocalContext.current

    // Revisa periódicamente si la conexión está activa
    val networkCallback = rememberUpdatedState(object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: android.net.Network) {
            super.onAvailable(network)
            isConnected = true
        }

        override fun onLost(network: android.net.Network) {
            super.onLost(network)
            isConnected = false
        }
    })

    // Registrar callback dentro del bloque composable
    LaunchedEffect(context) {
        connectivityManager.registerDefaultNetworkCallback(networkCallback.value)
    }

    val navController = rememberNavController()

    // Si no hay conexión, mostrar la pantalla de sin conexión
    if (isConnected) {
        Surface(color = Color.White) {
            setupNavGraph(navController = navController)
        }
    } else {
        Surface(color = Color.White) {
            NoInternetScreen()
        }
    }
}

@Composable
fun NoInternetScreen() {
    // Fondo gris completo
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.nointernet), // Asegúrate de que la imagen esté en 'res/drawable'
                contentDescription = "No Internet Connection",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun setupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            Login(navController)
        }
        composable("Menu") {
            Menu(navController)
        }
        composable("Groups") {
            Groups(navController)
        }
        composable("Chats") {
            Chats(navController)
        }
        composable("Events") {
            Events(navController)
        }
        composable("User") {
            User(navController)
        }
        composable("NewGroup") {
            NewGroup(navController)
        }
        composable("NewEvents") {
            NewEvents(navController)
        }
        composable("NewSurvey") {
            NewSurvey(navController)
        }
        composable("EventDetail/{title}/{description}/{date}") { backStackEntry ->
            EventDetail(navController = navController, navBackStackEntry = backStackEntry)
        }
        composable("SurveyDetail/{title}") { backStackEntry ->
            SurveyDetail(navBackStackEntry = backStackEntry)
        }
        composable("EditUser") {
            EditUser(navController)
        }
        composable("chatView/{chatName}/{lastMessage}") { backStackEntry ->
            ChatView(navController = navController,navBackStackEntry = backStackEntry)
        }

        // Pantalla sin conexión
        composable("NoInternetScreen") {
            NoInternetScreen()
        }
    }
}
