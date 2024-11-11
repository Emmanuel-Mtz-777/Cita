package com.example.citaproyect.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.citaproyect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroup(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rules by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    // Scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    // Check screen width to adjust layout for tablets and larger screens
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isCompact = maxWidth < 600.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.darkMidnightBlue))
                .verticalScroll(scrollState)
                .padding(top = if (isCompact) 8.dp else 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(if (isCompact) 8.dp else 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(if (isCompact) 0.9f else 0.7f) // Adjust width based on screen size
                    .height(if (isCompact) 200.dp else 300.dp)
                    .background(Color.Gray)
                    .border(2.dp, Color.White)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageUri),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(text = "Imagen del grupo", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 16.dp))

            Text(
                text = "Crear Nuevo Grupo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = if (isCompact) 20.sp else 28.sp // Adjust font size for different screens
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(if (isCompact) 16.dp else 24.dp))

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(if (isCompact) 0.9f else 0.7f)
            )

            Spacer(modifier = Modifier.height(if (isCompact) 6.dp else 8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(if (isCompact) 0.9f else 0.7f)
            )

            Spacer(modifier = Modifier.height(if (isCompact) 6.dp else 8.dp))

            TextField(
                value = rules,
                onValueChange = { rules = it },
                label = { Text("Reglas") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(if (isCompact) 0.9f else 0.7f)
            )

            Spacer(modifier = Modifier.height(if (isCompact) 12.dp else 16.dp))

            Button(
                onClick = {
                    showConfirmation = true
                    navController.navigate("Groups")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth(if (isCompact) 0.6f else 0.4f)
            ) {
                Text("Guardar Grupo", color = Color.White)
            }

            if (showConfirmation) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Grupo creado con éxito", color = Color.White)
            }
        }
    }
}
