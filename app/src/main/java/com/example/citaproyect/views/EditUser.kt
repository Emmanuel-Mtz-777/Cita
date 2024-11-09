package com.example.citaproyect.views

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.citaproyect.R
import com.example.citaproyect.models.data.NavigationItem
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream



@Composable
fun EditUser(navController: NavController) {
    val selectedNavItem = remember { mutableStateOf(4) }
    val context = LocalContext.current

    // Variables para el estado de la imagen y solicitud de permisos
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showPermissionRequest by remember { mutableStateOf(false) }

    // Launcher para la cámara, que deben de estar antes porque si no el código no jala >:(
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            profileImageUri = saveBitmapToFile(it, context)
        }
    }

    // Launcher para la galería, que deben de estar antes porque si no el código no jala >:(
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        showPermissionRequest = false  // Cierra el request de permisos

        if (allGranted) {
            // Llama al diálogo de selección de imagen solo si los permisos fueron concedidos
            showImagePickerDialog(cameraLauncher, galleryLauncher)
        } else {
            // Maneja el caso donde los permisos fueron denegados
        }
    }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, selectedNavItem = selectedNavItem)
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
                        painter = painterResource(id = R.drawable.auron),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )

                    // Botón de edición (ícono de lápiz)
                    IconButton(
                        onClick = {
                            // Activa el estado para solicitar permisos
                            showPermissionRequest = true
                        },
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                            .background(colorResource(id = R.color.jellybean))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar RequestPermissions solo si showPermissionRequest es verdadero
                if (showPermissionRequest) {
                    RequestPermissions(
                        context = context,
                        permissionLauncher = permissionLauncher,
                        action = {
                            showPermissionRequest = false
                        }
                    )
                }

                // Campos de texto de edición
                var name by remember { mutableStateOf("Pepito Ramirez Foraneo") }
                var description by remember { mutableStateOf("Abre tu menteeeeee 🧠") }

                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                )

                BasicTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
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


@Composable
fun showImagePickerDialog(
    cameraLauncher: ActivityResultLauncher<Void?>,
    galleryLauncher: ActivityResultLauncher<String>
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Seleccionar Imagen") },
            text = { Text(text = "Elige una opción para seleccionar una imagen.") },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        cameraLauncher.launch(null)  // Abre la cámara
                    }
                ) {
                    Text("Abrir Cámara")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        galleryLauncher.launch("image/*")  // Abre la galería
                    }
                ) {
                    Text("Abrir Galería")
                }
            }
        )
    }
}


@Composable
fun RequestPermissions(
    context: Context,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    action: @Composable () -> Unit
) {
    // Solicita permisos de cámara y almacenamiento
    permissionLauncher.launch(
        arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    // Ejecuta la acción en un contexto @Composable después de solicitar permisos
    action()
}

// Método para guardar Bitmap en un archivo y obtener un Uri
fun saveBitmapToFile(bitmap: Bitmap, context: Context): Uri? {
    val filename = "profile_picture.jpg"
    val file = File(context.cacheDir, filename)
    var outputStream: OutputStream? = null
    try {
        outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        outputStream?.close()
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}





