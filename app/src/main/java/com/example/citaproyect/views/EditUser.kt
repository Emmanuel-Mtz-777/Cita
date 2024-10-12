package com.example.citaproyect.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



@Composable
fun EditUser(navController: NavController) {
    var name by remember { mutableStateOf("Pepito Ramirez Foraneo") }
    var description by remember { mutableStateOf("Abre tu menteeeeee 🧠") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Editar Perfil", fontSize = 24.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

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

        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
        ) {
            Text(text = "Guardar Cambios", color = Color.White)
        }
    }
}
