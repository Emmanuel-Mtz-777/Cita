package com.example.citaproyect.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.citaproyect.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEvents(navController: NavController, usuarioId: String?) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    // Scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.darkMidnightBlue))
            .verticalScroll(scrollState), // Enable vertical scrolling
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Botón de regreso o salir
        Button(
            onClick = {
                // Navega de regreso a la vista de eventos
                navController.navigate("Events/$usuarioId")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Salir",
                tint = Color.White
            )
        }

        // Imagen del evento
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 600.dp

            Box(
                modifier = Modifier
                    .fillMaxWidth(if (isCompact) 0.9f else 0.7f) // Ajustar el ancho según el tamaño de la pantalla
                    .height(if (isCompact) 200.dp else 300.dp)
                    .background(Color.Gray)
                    .border(2.dp, Color.White)
                    .align(Alignment.Center)
                    .clickable { imagePickerLauncher.launch("image/*") },

            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageUri),
                        contentDescription = " Imagen seleccionada",
                        modifier = Modifier.fillMaxSize()
                            .wrapContentWidth(Alignment.CenterHorizontally), // Esto asegura que la imagen se centre horizontalmente
                        contentScale = ContentScale.Crop // Asegura que la imagen no se distorsione,

                    )
                } else {
                    Text(text = "Selecciona una Imagen para el Evento", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
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
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp) // Ajusta el margen superior
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Campo para el Título
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo para la Descripción
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Botón para seleccionar la fecha
            Button(
                onClick = {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    android.app.DatePickerDialog(
                        context,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        },
                        year,
                        month,
                        day
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(if (date.isEmpty()) "Seleccionar Fecha" else date, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar el evento
            Button(
                onClick = {
                    showConfirmation = true
                    // Lógica para guardar el evento

                    // Después de guardar, puedes volver a la vista de eventos
                    navController.navigate("Events/$usuarioId")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text("Guardar Evento", color = Color.White)
            }

            // Mensaje de confirmación
            if (showConfirmation) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Evento guardado con éxito", color = Color.White)
            }
        }
    }
}