package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun NewGroup(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rules by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var showImageDialog by remember { mutableStateOf(false) } // Estado para mostrar el modal de imagen

    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(color = colorResource(id = R.color.darkMidnightBlue)), // Color de fondo de la columna
        verticalArrangement = Arrangement.Center, // Centra verticalmente el contenido
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente el contenido
    ) {
        // Rectángulo para subir imágenes (simulación)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray) // Color del fondo del rectángulo
                .border(2.dp, Color.White) // Borde blanco
                .padding(16.dp) // Espaciado interno
                .clickable { showImageDialog = true }, // Muestra el modal al hacer clic
            contentAlignment = Alignment.Center // Centra el contenido
        ) {
            Text(text = "Imagen del grupo", color = Color.White) // Texto dentro del rectángulo
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Crear Nuevo Grupo",
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

        // Campo para las Reglas
        TextField(
            value = rules,
            onValueChange = { rules = it },
            label = { Text("Reglas") },
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
                navController.navigate("Groups")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Botón transparente
        ) {
            Text("Guardar Grupo", color = Color.White) // Texto blanco en el botón
        }

        // Mensaje de confirmación
        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del mensaje
            Text(text = "Grupo creado con éxito", color = Color.White)
        }
    }

    // Modal para mostrar que la imagen se ha cargado
    if (showImageDialog) {
        AlertDialog(
            onDismissRequest = { showImageDialog = false },
            title = { Text("Imagen Cargada") },
            text = { Text("Imagen cargada con éxito.") },
            confirmButton = {
                Button(onClick = { showImageDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}
