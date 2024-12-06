package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mensajeER",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["emisorUsuarioId"], // Clave foránea que actúa como emisor
            onDelete = ForeignKey.CASCADE // Elimina los mensajes si el usuario emisor es eliminado
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["receptorUsuarioId"], // Clave foránea que actúa como receptor
            onDelete = ForeignKey.CASCADE // Elimina los mensajes si el usuario receptor es eliminado
        )
    ]
)
data class MensajeER(
    @PrimaryKey(autoGenerate = true) val IdMensajeER: Int = 0, // Llave primaria para la tabla
    val emisorUsuarioId: Int, // Clave foránea para el emisor, referencia a IdUsuario
    val receptorUsuarioId: Int, // Clave foránea para el receptor, referencia a IdUsuario
    val mensaje: String, // Contenido del mensaje
    val fechaHora: String // Fecha y hora en que se envió el mensaje (puedes usar LocalDateTime con TypeConverter)
)