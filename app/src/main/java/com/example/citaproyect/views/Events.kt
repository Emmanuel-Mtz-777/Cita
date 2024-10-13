package com.example.citaproyect.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Events(navController: NavController) {
    val selectedItem = remember { mutableStateOf(3) }
    val searchText = remember { mutableStateOf("") }

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
            Column {
                FloatingActionButton(
                    onClick = { navController.navigate("NewEvents") },
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.jellybean)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Event"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(
                    onClick = { navController.navigate("NewSurvey") },
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.jellybean)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Add Survey"
                    )
                }
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
                    .padding(16.dp)
            ) {
                // Barra de búsqueda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.darkMidnightBlue), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = colorResource(id = R.color.jellybean)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchText.value,
                            onValueChange = { searchText.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
                            placeholder = {
                                Text(text = "Buscar...", color = Color.LightGray)  // Placeholder
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Secciones separadas de eventos y encuestas con desplazamiento independiente
                Row(modifier = Modifier.fillMaxSize()) {
                    // Lista de Eventos
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        item {
                            EventCard(
                                title = "Fiesta de bienvenida",
                                description = "Publicado por: Capítulo Tics",
                                date = "30-02-25",
                                imageRes = R.drawable.ic_launcher_background,
                                onClick = {
                                    navController.navigate("EventDetail/Fiesta de bienvenida/Publicado por: Capítulo Tics/30-02-25")
                                }
                            )
                        }
                        item {
                            EventCard(
                                title = "Simposio",
                                description = "Publicado por: Coordinación Tics",
                                date = "Por definir",
                                imageRes = R.drawable.ic_launcher_foreground,
                                onClick = {
                                    navController.navigate("EventDetail/Simposio/Publicado por: Coordinación Tics/Por definir")
                                }
                            )
                        }
                        item {
                            EventCard(
                                title = "Hackathon Universitario",
                                description = "Publicado por: Universidad",
                                date = "10-03-25",
                                imageRes = R.drawable.ic_launcher_foreground,
                                onClick = {
                                    navController.navigate("EventDetail/HackathonUniversitario/Publicado por: Universidad/10-03-25")
                                }
                            )
                        }
                        item {
                            EventCard(
                                title = "Torneo de programación",
                                description = "Publicado por: Facultad de Ingenierías",
                                date = "15-03-25",
                                imageRes = R.drawable.ic_launcher_background,
                                onClick = {
                                    navController.navigate("EventDetail/TorneoDeProgramación/Publicado por: Facultad de Ingenierías/15-03-25")
                                }
                            )
                        }
                    }

                    // Lista de Encuestas
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        item {
                            SurveyCard(
                                title = "Encuesta sobre tecnologías emergentes",
                                description = "Participa en la encuesta sobre nuevas tecnologías.",
                                onClick = {
                                    navController.navigate("SurveyDetail/EncuestaTecnologiasEmergentes")
                                }
                            )
                        }
                        item {
                            SurveyCard(
                                title = "Satisfacción del simposio",
                                description = "Cuéntanos qué te pareció el simposio.",
                                onClick = {
                                    navController.navigate("SurveyDetail/SatisfaccionDelSimposio")
                                }
                            )
                        }
                        item {
                            SurveyCard(
                                title = "Preferencias para Hackathon",
                                description = "Dinos tus intereses para participar.",
                                onClick = {
                                    navController.navigate("SurveyDetail/PreferenciasParaHackathon")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(title: String, description: String, date: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .background(color = Color.Transparent)
            .clickable { onClick() }
            .animateContentSize(animationSpec = tween(300)),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.darkMidnightBlue))
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = date,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SurveyCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .background(color = Color.Transparent)
            .clickable { onClick() }
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.darkMidnightBlue))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}