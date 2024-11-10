package com.example.citaproyect.views

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Edit
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditUser(navController: NavController) {
    val context = LocalContext.current
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNavItem by remember { mutableStateOf(0) }

    // Configuración de permisos para cámara y galería
    val permissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)

    // Lanzador para tomar una foto desde la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            profileImageUri = saveBitmapToFile(bitmap, context)
        }
    }

    // Lanzador para seleccionar una imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedNavItem = remember { mutableStateOf(selectedNavItem) }
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Editar Perfil",
                    fontSize = 35.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Imagen de perfil con botón de edición
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.size(150.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = profileImageUri ?: R.drawable.auron),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )

                    // Botón para editar la imagen de perfil
                    IconButton(
                        onClick = {
                            if (permissionsState.allPermissionsGranted) {
                                showImagePickerDialog = true
                            } else {
                                permissionsState.launchMultiplePermissionRequest()
                            }
                        },
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.jellybean))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para editar el nombre
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                )

                // Campo para editar la descripción
                BasicTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.jellybean))
                ) {
                    Text(
                        text = "Guardar Cambios",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

    // Mostrar el diálogo de selección de imagen solo si los permisos están concedidos
    if (showImagePickerDialog) {
        ShowImagePickerDialog(
            cameraLauncher = { cameraLauncher.launch(null) },
            galleryLauncher = { galleryLauncher.launch("image/*") },
            onDismiss = { showImagePickerDialog = false }
        )
    }

}

@Composable
fun ShowImagePickerDialog(
    cameraLauncher: () -> Unit,
    galleryLauncher: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                cameraLauncher()
                onDismiss()
            }) {
                Text("Tomar Foto") // Texto del botón para tomar foto
            }
        },
        dismissButton = {
            TextButton(onClick = {
                galleryLauncher()
                onDismiss()
            }) {
                Text("Seleccionar desde Galería") // Texto del botón para la galería
            }
        },
        title = { Text("Selecciona una opción") },
        text = { Text("Elige si deseas tomar una foto o seleccionar desde la galería") },
        properties = DialogProperties()
    )
}

// Función para guardar el bitmap de la cámara en un archivo temporal
fun saveBitmapToFile(bitmap: Bitmap, context: Context): Uri? {
    val filename = "profile_picture.jpg"
    val file = File(context.cacheDir, filename)
    return try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}









@Composable
fun BottomNavigationBar(navController: NavController, selectedNavItem: MutableState<Int>) {
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
                selected = selectedNavItem.value == index,
                onClick = {
                    selectedNavItem.value = index
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
                        tint = if (selectedNavItem.value == index) colorResource(id = R.color.jellybean) else Color.White
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







