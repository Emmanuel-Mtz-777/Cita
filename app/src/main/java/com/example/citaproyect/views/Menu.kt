package com.example.citaproyect.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citaproyect.R

@Composable
fun Menu(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.darkMidnightBlue)
            ) {
                val items = listOf(
                    BottomNavItem("Home", Icons.Filled.Home),
                    BottomNavItem("Groups", Icons.Filled.Search),
                    BottomNavItem("Chats", Icons.Filled.Person),
                    BottomNavItem("Events", Icons.Filled.Settings),
                    BottomNavItem("User", Icons.Filled.Info)
                )

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem.value == index,
                        onClick = {
                            if (selectedItem.value != index) {
                                selectedItem.value = index
                                // Navegar a la pantalla correspondiente
                                val route = when (item.label) {
                                    "Home" -> "Menu"      // Pantalla de inicio
                                    "Groups" -> "Groups"  // Pantalla de grupos
                                    "Chats" -> "Chats"    // Pantalla de chats
                                    "Events" -> "Events"  // Pantalla de eventos
                                    "User" -> "User"      // Pantalla de usuario
                                    else -> "Menu"        // Por defecto a la pantalla de menú
                                }
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
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
                                color = Color.White
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
                            colorResource(id = R.color.black),
                            colorResource(id = R.color.darkMidnightBlue),
                            colorResource(id = R.color.richBlack)
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

data class BottomNavItem(val label: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun navbar() {
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    val navbarColor = colorResource(id = R.color.navbar).copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = navbarColor)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    placeholder = { Text("Buscar...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = navbarColor,  // Usar el color con opacidad
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .background(color = navbarColor)
                )
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
