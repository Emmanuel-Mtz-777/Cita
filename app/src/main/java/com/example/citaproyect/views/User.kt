package com.example.citaproyect.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

@Composable
fun User(navController: NavController) {
    val selectedItem = remember { mutableStateOf(4) }

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
                                "Home" -> "Menu"
                                "Groups" -> "Groups"
                                "Chats" -> "Chats"
                                "Events" -> "Events"
                                "User" -> "User"
                                else -> "Groups"
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen de perfil
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.auron),
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { /**/ },
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile Picture",
                            tint = Color.DarkGray
                        )
                    }
                }

                // Nombre y ID
                Text(
                    text = "Pepito Ramirez Foraneo",
                    fontSize = 22.sp,
                    color = Color.White
                )
                Text(
                    text = "21151909",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Información del usuario
                ProfileInfoField(label = "Carrera:", info = "Ingeniería Gestión Empresarial")
                ProfileInfoField(label = "Semestre:", info = "3ro")
                ProfileInfoField(label = "Descripción:", info = "Abre tu menteeeeee \uD83D\uDED0")

                Spacer(modifier = Modifier.height(16.dp))

                // Preferencias del usuario (Chips)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Chip(label = "Malinche")
                    Chip(label = "BTS")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Editar Perfil
                Button(
                    onClick = { /**/ },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(
                            id = R.color.jellybean)),
                ) {
                    Text(text = "Editar Perfil", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoField(label: String, info: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color.White, fontSize = 20.sp)
        Text(
            text = info,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(Color.White.copy(alpha = 0.1f))
                .padding(8.dp),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun Chip(label: String) {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.darkMidnightBlue), CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 14.sp)
    }
}

