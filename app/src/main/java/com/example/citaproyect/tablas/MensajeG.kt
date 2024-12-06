package com.example.citaproyect.tablas


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mensajeG",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Clave foránea que hace referencia al usuario
            onDelete = ForeignKey.CASCADE // Elimina el mensaje si el usuario es eliminado
        ),
        ForeignKey(
            entity = Grupo::class, // Referencia a la entidad Grupo
            parentColumns = ["IdGrupo"], // Columna primaria en Grupo
            childColumns = ["IdGrupo"], // Clave foránea que hace referencia al grupo
            onDelete = ForeignKey.CASCADE // Elimina el mensaje si el grupo es eliminado
        )
    ]
)
data class MensajeG(
    @PrimaryKey(autoGenerate = true) val IdMensajeG: Int = 0, // Llave primaria para la tabla
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val IdGrupo: Int, // Clave foránea que hace referencia a la tabla Grupo
    val mensaje: String, // Contenido del mensaje
    val fechaHora: String // Fecha y hora en que se envió el mensaje (puedes usar LocalDateTime con TypeConverter)
)