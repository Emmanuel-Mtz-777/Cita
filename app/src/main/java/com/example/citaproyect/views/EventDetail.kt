package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.citaproyect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetail(navBackStackEntry: NavBackStackEntry) {
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
                    IconButton(onClick = { /* Manejar navegación hacia atrás */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Icono de Regreso", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.darkMidnightBlue)) // Usar color del recurso
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(id = R.color.richBlack)) // Usar color del recurso
                .padding(16.dp),
            horizontalAlignment = Alignment.Start // Alinear el contenido a la izquierda
        ) {
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