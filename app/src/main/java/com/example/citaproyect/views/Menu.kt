package com.example.citaproyect.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.GroupsModel

@Composable
fun Menu(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    val groups = listOf(
        GroupsModel("Ciencia y Tecnología", R.drawable.ciencias, "Un laboratorio de ideas donde la innovación y el descubrimiento son la norma. ¡Únete a nosotros!", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Arte y Diseño", R.drawable.arte, "Un refugio creativo donde las ideas florecen y la imaginación no tiene límites. ¡Deja que tu talento brille!", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Aventuras al Aire Libre", R.drawable.aire, "Un grupo de exploradores que buscan la adrenalina y la emoción en cada rincón de la naturaleza.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones."),
        GroupsModel("Música y Expresión", R.drawable.music, "Un coro de voces donde la creatividad y la armonía se unen para celebrar el arte de la música.", "Reglas: No contenido +18, Respeto entre miembros, Asistencia a las sesiones.")
    )
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
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()), // Añadir el modificador verticalScroll
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                navbar()
                Text(
                    text = "Nuevos Anuncios",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                EventsMenu(navController)
                Text(
                    text = "Grupos recomendados",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                        .horizontalScroll(rememberScrollState()),
                ) {
                    groups.take(4).forEach { groupItem ->
                        GroupRowHorizontal(group = groupItem, navController)
                    }
                }
                Text(
                    text = "Encuestas recientes   ",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                SurveyMenu(navController)
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun navbar() {
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    val navbarColor = colorResource(id = R.color.black).copy(alpha = 0.3f)

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

@Composable
fun EventsMenu(navController: NavController) {
    // Una columna para organizar los dos eventos
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Primer evento
        EventCard(
            title = "Conferencia sobre IA",
            description = "Publicado por: Facultad de Ciencias",
            date = "01-04-25",
            imageRes = R.drawable.ic_launcher_background,
            onClick = {
                navController.navigate("EventDetail/ConferenciaIA/Publicado por: Facultad de Ciencias/01-04-25")
            }
        )
        // Separación entre los eventos
        Spacer(modifier = Modifier.height(10.dp))
        // Segundo evento
        EventCard(
            title = "Taller de Kotlin",
            description = "Publicado por: Club de Programación",
            date = "05-04-25",
            imageRes = R.drawable.ic_launcher_foreground,
            onClick = {
                navController.navigate("EventDetail/TallerKotlin/Publicado por: Club de Programación/05-04-25")
            }
        )
    }
}

@Composable
fun GroupsRecomended(group: GroupsModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Espaciado entre tarjetas
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
                .clip(RoundedCornerShape(25.dp))
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
        JoinGroupDialog(
            group = group,
            onDismiss = { showDialog = false }
        )
    }
}


@Composable
fun SurveyMenu(navController: NavController) {
    // Una columna para organizar los dos eventos
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        SurveyCard(
            title = "Encuesta sobre tecnologías emergentes",
            description = "Participa en la encuesta sobre nuevas tecnologías.",
            onClick = {
                navController.navigate("SurveyDetail/EncuestaTecnologiasEmergentes")
            }
        )
        // Separación entre los eventos
        Spacer(modifier = Modifier.height(10.dp))
        // Segundo evento
        SurveyCard(
            title = "Satisfacción del simposio",
            description = "Cuéntanos qué te pareció el simposio.",
            onClick = {
                navController.navigate("SurveyDetail/SatisfaccionDelSimposio")
            }
        )
    }
}