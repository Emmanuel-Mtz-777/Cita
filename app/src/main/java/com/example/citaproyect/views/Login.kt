package com.example.citaproyect.views
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController) {
    Column {
        Text(text = "Listo Humberto, ya tienes el botón >:(, ya tuviste lo que querías, " +
                "así que vete, ouiiii, ouiiiiiiiIiIiIiII :(")
        Button(onClick = { navController.navigate("Menu") }) {
            Text(text = "Entrar")
        }
    }
}