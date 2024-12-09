package com.example.citaproyect.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

data class Chat(val name: String, val lastMessage: String, val profileImage: Int)

@Composable
fun Chats(navController: NavController, usuarioId: String?) {
    val usuarioId = usuarioId
    val selectedItem = remember { mutableStateOf(2) }
    val chatList = listOf(
        Chat("John Doe", "Hey, how's it going?", R.drawable.ic_launcher_foreground),
        Chat("Jane Smith", "Are we meeting tomorrow?", R.drawable.ic_launcher_background),
        Chat("Chris Evans", "Don't forget the project!", R.drawable.ic_launcher_background),
        Chat("Emma Watson", "See you later!", R.drawable.ic_launcher_foreground),
        Chat("Robert Brown", "Check out this link!", R.drawable.ic_launcher_foreground),
        Chat("Sophia Johnson", "Thanks for the update!", R.drawable.ic_launcher_background),
        Chat("Michael Lee", "I'll call you later.", R.drawable.ic_launcher_foreground),
        Chat("Olivia Martinez", "Let's grab lunch tomorrow!", R.drawable.ic_launcher_foreground),
        Chat("Liam Wilson", "Got the documents ready.", R.drawable.ic_launcher_background),
        Chat("Ava White", "Just finished the meeting.", R.drawable.ic_launcher_background),
        Chat("Groups Chat", "Just finished the meeting.", R.drawable.ic_launcher_background)
    )

    Scaffold(
        bottomBar = {
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
                                "Home" -> "Menu/$usuarioId"
                                "Groups" ->  "Groups/$usuarioId"
                                "Chats" -> "Chats/$usuarioId"
                                "Events" -> "Events/$usuarioId"
                                "User" -> "User/$usuarioId"
                                else -> "Menu/$usuarioId"
                            }
                            navController.navigate(route)
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.darkMidnightBlue),
                            colorResource(id = R.color.richBlack),
                            colorResource(id = R.color.prussianBlue)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chats",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(text = "El ID del usuario es: ${usuarioId ?: "Desconocido"}")
                Spacer(modifier = Modifier.height(16.dp))

                // Lista de chats con foto de perfil y desplazamiento vertical
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(chatList) { chat ->
                        ChatItem(chat = chat, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium) // Efecto flotante
            .background(color = Color.Transparent)
            .clickable {
                // Navegar a la vista del chat seleccionado y pasar el último mensaje
                navController.navigate("chatView/${chat.name}/${chat.lastMessage}")
            }
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.darkMidnightBlue))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de perfil
            Image(
                painter = painterResource(id = chat.profileImage),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray) // Puedes cambiar el color de fondo si no carga la imagen
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = chat.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = chat.lastMessage,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}