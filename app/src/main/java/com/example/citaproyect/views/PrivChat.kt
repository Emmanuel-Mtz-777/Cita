package com.example.citaproyect.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.citaproyect.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(navBackStackEntry: NavBackStackEntry) {
    val chatName = navBackStackEntry.arguments?.getString("chatName") ?: "Chat"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = chatName,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White // Color del título del chat
                            )
                            Text(
                                text = "Active", // Estado del contacto
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Green // Estado del contacto
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.darkMidnightBlue), // Cambia este color a uno oscuro
                    actionIconContentColor = Color.White, // Color de los iconos en la barra
                    titleContentColor = Color.White // Color del texto del título
                )
            )
        },
        bottomBar = {
            BottomChatInput()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.richBlack)) // Fondo de la columna principal
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Esto hace que la lista ocupe todo el espacio disponible
                    .padding(16.dp)
            ) {
                items(2) { index -> // Cambia esto para obtener tus mensajes
                    when (index) {
                        0 -> ChatBubble(
                            message = "We haven't seen you for 2 weeks. Would you like to set up an appointment with your nutritionist?",
                            isSentByUser = false,
                            timestamp = "08:30 PM"
                        )
                        1 -> ChatBubble(
                            message = "OK, let's make an appointment.",
                            isSentByUser = true,
                            timestamp = "08:31 PM"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isSentByUser: Boolean, timestamp: String) {
    val bubbleColor = if (isSentByUser) colorResource(id = R.color.jellybean) else Color(0xFFE3F2FD)
    val textColor = if (isSentByUser) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
                .widthIn(max = 280.dp) // Controlar el ancho máximo de la burbuja
        ) {
            Text(text = message, color = textColor)
        }
        Text(
            text = timestamp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomChatInput() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.darkMidnightBlue))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Acción del ícono de imagen */ }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Photo",
                tint = Color.White
            )
        }

        TextField(
            value = "", // Cambia esto por el estado de tu mensaje
            onValueChange = { /* Actualizar el texto */ },
            placeholder = { Text("Enter your message", color = Color.White) },
            modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.prussianBlue), shape = MaterialTheme.shapes.small),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White
            )
        )

        IconButton(onClick = { /* Acción para enviar mensaje */ }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Message",
                tint = colorResource(id = R.color.jellybean)
            )
        }
    }
}