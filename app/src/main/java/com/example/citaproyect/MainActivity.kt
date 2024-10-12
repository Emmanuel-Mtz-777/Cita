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
import com.example.citaproyect.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMultiScreenApp()
        }
    }
}

@Composable
fun ComposeMultiScreenApp() {
    val navController = rememberNavController()
    Surface(color = Color.White) {
        setupNavGraph(navController = navController)
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
            EventDetail(navBackStackEntry = backStackEntry)
        }
        composable("SurveyDetail/{title}") { backStackEntry ->
            SurveyDetail(navBackStackEntry = backStackEntry)
        }

    }
}