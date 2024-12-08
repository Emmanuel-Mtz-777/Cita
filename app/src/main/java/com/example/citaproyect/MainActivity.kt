package com.example.citaproyect

import Login
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citaproyect.views.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Eliminar la validación de la conexión a internet

        setContent {
            ComposeMultiScreenApp()
        }
    }
}

@Composable
fun ComposeMultiScreenApp() {
    val navController = rememberNavController()

    // Directamente mostrar la navegación sin validar la conexión
    Surface(color = Color.White) {
        setupNavGraph(navController = navController)
    }
}

@Composable
fun setupNavGraph(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    NavHost(navController = navController, startDestination = "Login") {

        composable("Menu/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
            Menu(navController, usuarioId)
        }
        composable("Login") {
            Login(navController)
        }
        composable("LoginCreateAcccount") {
            LoginCreateAcccount(navController)
        }
        composable("LoginSesion") {
            LoginSesion(navController)
        }

        composable("Groups/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
            Groups(navController, usuarioId)
        }
        composable("Chats/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
            Chats(navController, usuarioId)
        }
        composable("Events/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
            Events(navController, usuarioId)
        }
        composable("User/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
            User(navController, usuarioId)
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
            SurveyDetail(navController = navController, navBackStackEntry = backStackEntry)
        }
        composable("EditUser") {
            EditUser(navController)
        }
        composable("chatView/{chatName}/{lastMessage}") { backStackEntry ->
            ChatView(navController = navController, navBackStackEntry = backStackEntry)
        }

        // No hay más pantalla de conexión sin Internet
    }
}
