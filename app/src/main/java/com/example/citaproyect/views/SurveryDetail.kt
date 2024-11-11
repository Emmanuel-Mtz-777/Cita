package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SurveyDetail(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    // Extraer los argumentos desde la ruta
    val title = navBackStackEntry.arguments?.getString("title") ?: "Título de la Encuesta"
    var showConfirmation by remember { mutableStateOf(false) }
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
        }
    ) { paddingValues ->
        // Agregar verticalScroll al Column para habilitar el desplazamiento
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(id = R.color.richBlack))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Aquí agregamos el verticalScroll
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

            // Estado para las respuestas seleccionadas
            val selectedColor = remember { mutableStateOf("") }
            val selectedGenre = remember { mutableStateOf("") }
            val selectedFood = remember { mutableStateOf("") }

            for ((index, question) in questions.withIndex()) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Opciones para cada pregunta
                options[index].forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Agregar RadioButton
                        val isSelected = when (index) {
                            0 -> selectedColor.value == option
                            1 -> selectedGenre.value == option
                            2 -> selectedFood.value == option
                            else -> false
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                when (index) {
                                    0 -> selectedColor.value = option
                                    1 -> selectedGenre.value = option
                                    2 -> selectedFood.value = option
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            Button(
                onClick = {
                    showConfirmation = true
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Color más oscuro
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Centra el botón horizontalmente
            ) {
                Text("Guardar Encuesta", color = Color.White)
            }
            // Mensaje de confirmación
            if (showConfirmation) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Evento guardado con éxito", color = Color.White)
            }
        }
    }}