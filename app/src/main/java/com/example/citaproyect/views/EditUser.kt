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
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.icons.filled.Edit
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditUser(navController: NavController) {
    val context = LocalContext.current
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Verificar si ya están concedidos los permisos
    val cameraPermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    val storagePermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    // Lanzador para tomar una foto desde la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = saveImageToGallery(bitmap, context)
            profileImageUri = uri
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
                navController = navController
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

                    // Botón para abrir el diálogo de selección de imagen
                    IconButton(
                        onClick = {
                            // Si los permisos están concedidos, abrir la cámara o galería
                            if (cameraPermissionGranted && storagePermissionGranted) {
                                showImagePickerDialog = true
                            } else {
                                // Si los permisos no están concedidos, omitir la solicitud
                                // Directamente abrir la cámara o la galería
                                showImagePickerDialog = true
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
                Text(
                    text = "Editar Nombre: ",
                    fontSize = 25.sp,
                    color = Color.White
                )
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
                Text(
                    text = "Editar Descripción:",
                    fontSize = 25.sp,
                    color = Color.White
                )
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

                // Botón para guardar cambios
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
        title = { Text("Selecciona una opción") },
        text = { Text("Elige si deseas tomar una foto o seleccionar desde la galería.") },
        confirmButton = {
            TextButton(onClick = {
                cameraLauncher() // Lanza la cámara
                onDismiss() // Cierra el diálogo
            }) {
                Text("Tomar Foto")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                galleryLauncher() // Lanza la galería
                onDismiss() // Cierra el diálogo
            }) {
                Text("Seleccionar desde Galería")
            }
        }
    )
}


// Función para guardar la imagen en la galería
fun saveImageToGallery(bitmap: Bitmap, context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "profile_picture.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    // Inserta la imagen en la galería
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        // Escribir el bitmap en el URI de la galería
        imageUri?.let { uri ->
            val outputStream = resolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            outputStream?.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return imageUri
}

