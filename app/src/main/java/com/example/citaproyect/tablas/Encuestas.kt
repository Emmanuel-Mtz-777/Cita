package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "encuestas",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Encuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las encuestas cuando se elimina el usuario
        )
    ]
)
data class Encuesta(
    @PrimaryKey(autoGenerate = true) val IdEncuesta: Int = 0, // Llave primaria para la tabla
    val Titulo: String, // Título de la encuesta
    val Descripcion: String, // Descripción de la encuesta
    val FechaCreacion: String, // Fecha de creación de la encuesta (podrías usar LocalDate con TypeConverter)
    val IdUsuario: Int // Clave foránea que hace referencia al IdUsuario de la tabla Usuario
)