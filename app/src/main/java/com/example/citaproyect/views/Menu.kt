package com.example.citaproyect.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.UsuarioViewModel
import com.example.citaproyect.models.data.GroupsModel

@Composable
fun Menu(navController: NavController, usuarioId: String?) {
    Log.d("Menu", "usuarioId recibido: $usuarioId")

    val selectedItem = remember { mutableStateOf(0) }
    val groups = listOf(
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
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Navbar()
                Text(text = "Usuario ID: $usuarioId", color = Color.Green)
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
                        .horizontalScroll(rememberScrollState())
                ) {
                    groups.take(7).forEach { groupItem ->
                        GroupRoHorizontal(group = groupItem, navController)
                    }
                }
                Text(
                    text = "Encuestas recientes",
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
fun Navbar() {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val navbarColor = colorResource(id = R.color.black).copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = navbarColor)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar...") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = navbarColor,
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
            IconButton(onClick = { /* acción de búsqueda */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun EventsMenu(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        EventCard(
            title = "Conferencia sobre IA",
            description = "Publicado por: Facultad de Ciencias",
            date = "01-04-25",
            imageRes = R.drawable.ic_launcher_background,
            onClick = {
                navController.navigate("EventDetail/ConferenciaIA/Publicado por: Facultad de Ciencias/01-04-25")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
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
fun SurveyMenu(navController: NavController) {
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
        Spacer(modifier = Modifier.height(10.dp))
        SurveyCard(
            title = "Satisfacción del simposio",
            description = "Cuéntanos qué te pareció el simposio.",
            onClick = {
                navController.navigate("SurveyDetail/SatisfaccionDelSimposio")
            }
        )
    }
}

@Composable
fun GroupRoHorizontal(group: GroupsModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) } // Estado del modal

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { showDialog = true } // Muestra el modal al hacer clic
    ) {
        Column {
            Image(
                painter = painterResource(id = group.imageResId),
                contentDescription = group.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = group.title,

                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    // Modal (AlertDialog) que tiene dos opciones
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Unirse al grupo") },
            text = { Text(text = "¿Deseas solicitar unirte a este grupo?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Acción para solicitar unirse al grupo
                        showDialog = false // Cierra el modal
                    }
                ) {
                    Text("Solicitar unirse")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Cerrar el modal sin hacer nada
                        showDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
