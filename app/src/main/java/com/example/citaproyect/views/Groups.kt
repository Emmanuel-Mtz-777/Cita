package com.example.citaproyect.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.GroupsModel
import com.example.citaproyect.models.data.NavigationItem

@Composable
fun Groups(navController: NavController) {
    val selectedItem = remember { mutableStateOf(1) }

    // Lista de grupos (incluyendo la tarjeta principal)
    val groups = listOf(
        GroupsModel("Group 1", R.drawable.estudiambres),
        GroupsModel("Group 2", R.drawable.operativos),
        GroupsModel("Group 3", R.drawable.caballeros),
        GroupsModel("Group 4", R.drawable.estudiambres),
        GroupsModel("Group 5", R.drawable.operativos),
        GroupsModel("Group 6", R.drawable.caballeros),
        GroupsModel("Group 7", R.drawable.estudiambres),
        GroupsModel("Group 8", R.drawable.operativos),
        GroupsModel("Group 9", R.drawable.caballeros),
        GroupsModel("Group 10", R.drawable.estudiambres) // Se agregan más grupos
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.darkMidnightBlue),
                navController = navController,
                selectedItem = selectedItem
            )
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
                    .padding(10.dp)
            ) {
                navbar()

                // Columna para agrupar el texto y los grupos
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Mis grupos",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                    // Muestra solo los primeros 3 grupos horizontalmente
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .horizontalScroll(rememberScrollState()),
                    ) {
                        groups.take(3).forEach { groupItem ->
                            GroupRowHorizontal(group = groupItem, navController)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Muestra todos los grupos en un LazyVerticalGrid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Dos columnas
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(groups) { groupItem -> // Muestra todos los grupos
                        GroupGridItem(group = groupItem, navController)
                    }
                }
            }
        }
    }
}

// Tarjeta principal en formato Row (imagen con texto centrado)
@Composable
fun GroupRowHorizontal(group: GroupsModel, navController: NavController) {
    Box(
        modifier = Modifier
            .width(300.dp) // Ajusta el ancho según tus necesidades
            .padding(5.dp)
            .clickable {
                navController.navigate("GroupDetail/${group.title}")
            },
        contentAlignment = Alignment.Center // Centra el contenido
    ) {
        Image(
            painter = painterResource(id = group.imageResId),
            contentDescription = group.title,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Text(
            text = group.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center) // Centra el texto sobre la imagen
                .padding(8.dp)
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.medium
                ) // Fondo semitransparente para mejor legibilidad
                .padding(8.dp)
        )
    }
}

// Tarjeta para los grupos en el Grid
@Composable
fun GroupGridItem(group: GroupsModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.medium) // Burbuja
            .clickable {
                navController.navigate("GroupDetail/${group.title}") // Cambia la ruta según necesites
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = group.imageResId),
            contentDescription = group.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(Color.Gray, shape = MaterialTheme.shapes.medium)
        )
        Text(
            text = group.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black, // Color del texto
            modifier = Modifier.padding(8.dp) // Espaciado entre la imagen y el texto
        )
    }
}

// Función para la barra de navegación
@Composable
fun NavigationBar(containerColor: Color, navController: NavController, selectedItem: MutableState<Int>) {
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Groups", Icons.Filled.Search),
        NavigationItem("Chats", Icons.Filled.Person),
        NavigationItem("Events", Icons.Filled.Settings),
        NavigationItem("User", Icons.Filled.Info)
    )

    NavigationBar(
        containerColor = containerColor
    ) {
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
