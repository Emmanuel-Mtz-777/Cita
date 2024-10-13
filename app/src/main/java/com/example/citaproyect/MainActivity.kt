package com.example.citaproyect


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.citaproyect.views.ChatView
import com.example.citaproyect.views.Login
import com.example.citaproyect.views.Menu
import com.example.citaproyect.views.Groups
import com.example.citaproyect.views.Events
import com.example.citaproyect.views.User
import com.example.citaproyect.views.Chats
import com.example.citaproyect.views.EditUser
import com.example.citaproyect.views.NewEvents
import com.example.citaproyect.views.NewGroup


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ComposeMultiScreenApp()
        }
    }
}


@Composable
fun ComposeMultiScreenApp(){
    val navController=rememberNavController()
    Surface (color = Color.White){
        setupNavGraph(navController=navController)
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

        }

    }
}