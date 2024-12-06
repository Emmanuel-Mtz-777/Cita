package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "opciones",
    foreignKeys = [
        ForeignKey(
            entity = Pregunta::class, // Referencia a la entidad Pregunta
            parentColumns = ["IdPregunta"], // Columna primaria en Pregunta
            childColumns = ["IdPregunta"], // Columna en Opcion que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las opciones cuando se elimina la pregunta
        )
    ]
)
data class Opcion(
    @PrimaryKey(autoGenerate = true) val IdOpcion: Int = 0, // Llave primaria para la tabla
    val IdPregunta: Int, // Clave foránea que hace referencia al IdPregunta de la tabla Pregunta
    val TextoOpcion: String // Texto de la opción de respuesta
)