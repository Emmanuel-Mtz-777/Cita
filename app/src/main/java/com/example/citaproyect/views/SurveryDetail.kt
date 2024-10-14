package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
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
import androidx.navigation.NavController
import com.example.citaproyect.models.data.NavigationItem
import com.example.citaproyect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyDetail(navBackStackEntry: NavBackStackEntry) {
    // Extraer los argumentos desde la ruta
    val title = navBackStackEntry.arguments?.getString("title") ?: "Título de la Encuesta"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Icono de Regreso", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.darkMidnightBlue))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(id = R.color.richBlack))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            // Simulando preguntas y opciones
            val questions = listOf(
                "¿Cuál es tu color favorito?",
                "¿Qué tipo de música prefieres?",
                "¿Cuál es tu comida favorita?"
            )

            val options = listOf(
                listOf("Rojo", "Azul", "Verde", "Amarillo"),
                listOf("Pop", "Rock", "Jazz", "Clásica"),
                listOf("Pizza", "Sushi", "Ensalada", "Tacos")
            )

            for ((index, question) in questions.withIndex()) {
                Text(text = question, style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))

                // Opciones para cada pregunta
                options[index].forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Puedes usar un Checkbox para que el usuario seleccione opciones
                        Text(text = option, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}