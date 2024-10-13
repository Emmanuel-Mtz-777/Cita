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
fun NewEvents(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(color = colorResource(id = R.color.darkMidnightBlue)), // Color de fondo de la columna
        verticalArrangement = Arrangement.Center, // Centra verticalmente el contenido
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente el contenido
    ) {
        // Título
        Text(
            text = "Crear Nuevo Evento",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Campo para el Título
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Fondo transparente
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la Descripción
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Fondo transparente
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la Fecha
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Fecha") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Fondo transparente
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar el evento
        Button(
            onClick = {
                showConfirmation = true
                // Lógica para guardar el evento
                // ...
                // Después de guardar, puedes volver a la vista de eventos
                navController.navigate("Events")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Botón transparente
        ) {
            Text("Guardar Evento", color = Color.White) // Texto blanco en el botón
        }

        // Mensaje de confirmación
        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del mensaje
            Text(text = "Evento guardado con éxito", color = Color.White)
        }
    }
}