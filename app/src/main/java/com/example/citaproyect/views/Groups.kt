package com.example.citaproyect.views // Asegúrate de que la ruta sea correcta

import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

@Composable
fun Groups(navController: NavController) {
    val selectedItem = remember { mutableStateOf(1) }

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("NewGroup")
                },
                containerColor = Color.White, // Color del FAB
                contentColor = colorResource(id = R.color.jellybean) // Color del icono dentro del FAB
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, // Usando el ícono de '+'
                    contentDescription = "Add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End // Posición del FAB a la derecha
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
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                navbar()
            }
        }
    }
}
