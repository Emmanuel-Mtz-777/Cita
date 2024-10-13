package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

@Composable
fun EditUser(navController: NavController) {
    val selectedItem = remember { mutableStateOf(4) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, selectedItem = selectedItem) }
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var name by remember { mutableStateOf("Pepito Ramirez Foraneo") }
                var description by remember { mutableStateOf("Abre tu menteeeeee 🧠") }

                Text(
                    text = "Editar Perfil",
                    fontSize = 24.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                //Campo para poder editar próximamente el nombre
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                        .padding(8.dp)
                )

                // Campo para editar próximamente la descripción
                BasicTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar los cambios y regresar
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.jellybean)
                    )
                ) {
                    Text(text = "Guardar Cambios", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, selectedItem: MutableState<Int>) {
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
                        color = Color.White
                    )
                }
            )
        }
    }
}
