package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citaproyect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSurvey(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var option1 by remember { mutableStateOf("") }
    var option2 by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.darkMidnightBlue)), // Color de fondo
        verticalArrangement = Arrangement.Center, // Centra verticalmente el contenido
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente el contenido
    ) {
        // Título
        Text(
            text = "Crear Nueva Encuesta",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Campo para el Título de la Encuesta
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título de la Encuesta") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la Pregunta
        TextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Pregunta") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la Opción 1
        TextField(
            value = option1,
            onValueChange = { option1 = it },
            label = { Text("Opción 1") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la Opción 2
        TextField(
            value = option2,
            onValueChange = { option2 = it },
            label = { Text("Opción 2") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar la encuesta
        Button(
            onClick = {
                showConfirmation = true
                // Lógica para guardar la encuesta
                // ...
                // Después de guardar, puedes volver a la vista de eventos
                navController.navigate("Events")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Botón transparente
        ) {
            Text("Guardar Encuesta", color = Color.White)
        }

        // Mensaje de confirmación
        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del mensaje
            Text(text = "Encuesta guardada con éxito", color = Color.White)
        }
    }
}