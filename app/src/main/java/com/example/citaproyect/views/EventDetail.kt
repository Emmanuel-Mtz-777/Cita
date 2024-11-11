package com.example.citaproyect.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetail(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    // Extraer los argumentos desde la ruta
    val title = navBackStackEntry.arguments?.getString("title") ?: "Título del Evento"
    val description = navBackStackEntry.arguments?.getString("description") ?: "Descripción no disponible"
    val date = navBackStackEntry.arguments?.getString("date") ?: "Fecha no disponible"
    val organizer = navBackStackEntry.arguments?.getString("organizer") ?: "Organizador no disponible"


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, color = Color.White) },
                navigationIcon = {

                    IconButton(onClick = {
                        navController.popBackStack() // Esto regresa a la vista anterior
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )

                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.darkMidnightBlue))
            )
        },
        bottomBar = {
            // Agregar una BottomBar
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(id = R.color.richBlack))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start // Alinear el contenido a la izquierda
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Fecha: $date", style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Organizador: $organizer", style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedItem = remember { mutableStateOf(3) }

    NavigationBar(
        containerColor = colorResource(id = R.color.darkMidnightBlue)
    ) {
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
                        "Home" -> "Menu"
                        "Groups" -> "Groups"
                        "Chats" -> "Chats"
                        "Events" -> "Events" // Aquí se navega de nuevo a "Events"
                        "User" -> "User"
                        else -> "Events"
                    }
                    navController.navigate(route) {
                        popUpTo(route) { inclusive = true } // Asegurar que se regrese limpiamente a "Events"
                    }
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
