package com.example.citaproyect.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.GroupsModel
import com.example.citaproyect.models.data.NavigationItem

@Composable
fun Groups(navController: NavController, usuarioId: String?,  usuarioId: String?) {
    val usuarioId = usuarioId
    val selectedItem = remember { mutableStateOf(1) } // Initial selected index for bottom navigation

    // Lista de grupos
    val groups = listOf(
        GroupsModel("Estudihambres", R.drawable.estudiambres, "Grupo de Estudios", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Operativos 1-2", R.drawable.operativos, "Sistemas Operativos 1-2", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Los Caballeros", R.drawable.caballeros, "Los Caballeros seguimos aqui", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Ciencia y Tecnología", R.drawable.ciencias, "Un laboratorio de ideas donde la innovación y el descubrimiento son la norma. ¡Únete a nosotros!", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Arte y Diseño", R.drawable.arte, "Un refugio creativo donde las ideas florecen y la imaginación no tiene límites. ¡Deja que tu talento brille!", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Aventuras al Aire Libre", R.drawable.aire, "Un grupo de exploradores que buscan la adrenalina y la emoción en cada rincón de la naturaleza.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Música y Expresión", R.drawable.music, "Un coro de voces donde la creatividad y la armonía se unen para celebrar el arte de la música.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Literatura y Escritura", R.drawable.lit, "Un santuario de letras donde las historias cobran vida y la escritura es una aventura compartida.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Salud y Bienestar", R.drawable.heailty, "Un grupo dedicado a promover la salud física y mental, compartiendo consejos y experiencias positivas.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Viajes y Exploración", R.drawable.viajes, "Un club de viajeros intrépidos listos para compartir historias y consejos sobre sus aventuras alrededor del mundo.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones.")
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("NewGroup/$usuarioId") },
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.jellybean)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Event"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
                    .verticalScroll(rememberScrollState())
            ) {
                Navbar()
                // Título de grupos
                Text(
                    text = "Mis grupos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = "El ID del usuario es: ${usuarioId ?: "Desconocido"}")
                // Fila de grupos en miniatura
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

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Más grupos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )

                LazyRow(
                    modifier = Modifier.padding(5.dp)
                ) {
                    items(groups.drop(3)) { groupItem ->
                        GroupRowHorizontal(group = groupItem, navController)
                    }
                }

            }
        }
    }
}

@Composable
fun GroupRowHorizontal(group: GroupsModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(420.dp)
            .padding(5.dp)
            .clickable {
                showDialog = true
            },
        contentAlignment = Alignment.Center
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
                .align(Alignment.TopCenter)
                .padding(8.dp)
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(8.dp)
        )
    }

    if (showDialog) {
        GroupDetailsDialog(
            navController = navController,
            group = group,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun GroupDetailsDialog(navController: NavController, group: GroupsModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = group.title) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Mostrar la imagen del grupo
                Image(
                    painter = painterResource(id = group.imageResId),
                    contentDescription = group.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = group.description)
                Text(text = group.rules)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                navController.navigate("chatView/${group.title}/${group.description}") // Navegar al chat del grupo
                onDismiss() // Cerrar el diálogo después de la navegación
            }) {
                Text("Ver grupo")
            }
        }
    )
}





@Composable
fun JoinGroupDialog(group: GroupsModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,

        title = { Text(text = "Unirse a ${group.title}") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("¿Te gustaría unirte a este grupo?")
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = group.imageResId),
                    contentDescription = group.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = group.description)
                Text(text = group.rules)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // Manejar la lógica de unirse al grupo aquí
                onDismiss()
            }) {
                Text("Solicitar Unirse")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewGroups() {
    val navController = rememberNavController() // Necesitamos un NavController para las rutas de navegación
    Groups(navController = navController, usuarioId = "12345")
}
