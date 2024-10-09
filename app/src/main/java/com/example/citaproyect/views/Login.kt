package com.example.citaproyect.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citaproyect.R


@Composable
fun Login(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(colorResource(id = R.color.darkMidnightBlue)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Espacio para el logo


        // Campo de nombre de usuario
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                //.fillMaxWidth()
                .background(Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

// Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .background(Color.White),
                //.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(
                        id = android.R.drawable.ic_menu_view) // icono para mostrar contraseña
                else
                    painterResource(
                        id = android.R.drawable.ic_secure) // icono para ocultar contraseña

                Icon(
                    painter = image,
                    contentDescription =
                    if (passwordVisible) "Hide password"
                    else "Show password",
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox "Remember me"
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Text(
                text = "Remember me",
                color = Color.White,
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Botón de "Sign in"
        Button(
            onClick = { navController.navigate("Menu") },
            //modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign in",
                fontSize = 23.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Texto "Forgot password?"
        Text(
            text = "Forgot password?",
            color = colorResource(id = R.color.carolinaBlue),
            fontSize = 20.sp,
            modifier = Modifier.clickable
                {
            }
        )
    }
}
