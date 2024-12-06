package com.example.citaproyect.tablas


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "preguntas",
    foreignKeys = [
        ForeignKey(
            entity = Encuesta::class, // Referencia a la entidad Encuesta
            parentColumns = ["IdEncuesta"], // Columna primaria en Encuesta
            childColumns = ["IdEncuesta"], // Columna en Pregunta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las preguntas cuando se elimina la encuesta
        )
    ]
)
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val IdPregunta: Int = 0, // Llave primaria para la tabla
    val IdEncuesta: Int, // Clave foránea que hace referencia al IdEncuesta de la tabla Encuesta
    val TextoPregunta: String // Texto de la pregunta
)
