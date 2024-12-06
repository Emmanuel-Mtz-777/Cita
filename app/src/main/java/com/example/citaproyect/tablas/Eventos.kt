package com.example.citaproyect.tablas


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "eventos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Evento que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina los eventos cuando se elimina el usuario
        )
    ]
)
data class Evento(
    @PrimaryKey(autoGenerate = true) val IdEvento: Int = 0, // Llave primaria para la tabla
    val Nombre: String, // Nombre del evento
    val Descripcion: String, // Descripción del evento
    val Lugar: String, // Lugar del evento
    val Fecha: String, // Fecha del evento (también podría ser LocalDate con TypeConverter)
    val IdUsuario: Int // Clave foránea que hace referencia al IdUsuario de la tabla Usuario
)
