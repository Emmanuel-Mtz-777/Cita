package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "respuestas",
    foreignKeys = [
        ForeignKey(
            entity = Pregunta::class, // Referencia a la entidad Pregunta
            parentColumns = ["IdPregunta"], // Columna primaria en Pregunta
            childColumns = ["IdPregunta"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina la pregunta
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina el usuario
        ),
        ForeignKey(
            entity = Opcion::class, // Referencia a la entidad Opcion (si la tienes definida)
            parentColumns = ["IdOpcion"], // Columna primaria en Opcion
            childColumns = ["IdOpcion"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina la opción
        )
    ]
)
data class Respuesta(
    @PrimaryKey(autoGenerate = true) val IdRespuesta: Int = 0, // Llave primaria para la tabla
    val IdPregunta: Int, // Clave foránea que hace referencia a la tabla Pregunta
    val IdOpcion: Int, // Clave foránea que hace referencia a la tabla Opcion
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val FechaRespuesta: String // Fecha en que se dio la respuesta (puedes usar LocalDate si prefieres)
)
