package com.example.citaproyect.views

import androidx.core.app.NotificationCompat
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.citaproyect.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val chatName = navBackStackEntry.arguments?.getString("chatName") ?: "Chat"
    val lastMessage = navBackStackEntry.arguments?.getString("lastMessage") ?: "No messages"

    // Lista de mensajes que incluye el mensaje y la hora de envío
    val messages = remember { mutableStateListOf<Pair<String, String>>() }
    var currentMessage by remember { mutableStateOf(TextFieldValue("")) }

    // Obtener el contexto solo una vez
    val context = LocalContext.current

    // Función para enviar un mensaje
    val sendMessage: () -> Unit = {
        if (currentMessage.text.isNotBlank()) {
            // Obtener la hora actual
            val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

            // Agregar el mensaje y la hora a la lista
            messages.add(Pair(currentMessage.text, currentTime))
            currentMessage = TextFieldValue("")  // Limpiar el campo de texto

            // Verificar si los permisos de notificación están concedidos
            if (isNotificationPermissionGranted(context)) {
                showNotification(context, chatName, messages.last().first)  // Mostrar el último mensaje en la notificación
            } else {
                requestNotificationPermission(context)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack() // Esto regresa a la vista anterior
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = chatName,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Green
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.darkMidnightBlue),
                    actionIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomChatInput(
                currentMessage,
                onMessageChange = { currentMessage = it },
                onSendMessage = sendMessage
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.richBlack))
        ) {
            if (lastMessage.isNotEmpty()) {
                ChatBubble(
                    message = lastMessage,
                    isSentByUser = false,
                    timestamp = "08:30 PM"
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(messages.size) { index ->
                    val (message, timestamp) = messages[index]
                    ChatBubble(
                        message = message,
                        isSentByUser = index % 2 == 0,
                        timestamp = timestamp
                    )
                }
            }
        }
    }
}

// Composable para la entrada de texto en el chat
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomChatInput(
    currentMessage: TextFieldValue,
    onMessageChange: (TextFieldValue) -> Unit,
    onSendMessage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.darkMidnightBlue))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Acción del ícono de imagen */ }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Photo",
                tint = Color.White
            )
        }

        BasicTextField(
            value = currentMessage,
            onValueChange = onMessageChange,
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.prussianBlue), shape = MaterialTheme.shapes.small),
        )

        IconButton(onClick = { onSendMessage() }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Message",
                tint = colorResource(id = R.color.jellybean)
            )
        }
    }
}

// Composable para la burbuja del mensaje
@Composable
fun ChatBubble(message: String, isSentByUser: Boolean, timestamp: String) {
    val bubbleColor = if (isSentByUser) colorResource(id = R.color.jellybean) else Color(0xFFE3F2FD)
    val textColor = if (isSentByUser) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(text = message, color = textColor)
        }
        Text(
            text = timestamp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Función para verificar si los permisos de notificación están otorgados
fun isNotificationPermissionGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

// Función para solicitar permisos de notificación
fun requestNotificationPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
    }
}

// Función para mostrar la notificación
fun showNotification(context: Context, chatName: String, message: String) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "chat_notifications",
            "Chat Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for chat messages"
        }
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, "chat_notifications")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("New message from $chatName")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(1, notification)
}
