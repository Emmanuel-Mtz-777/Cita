package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "amigos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["usuarioPrincipal"], // Clave foránea que actúa como usuario principal
            onDelete = ForeignKey.CASCADE // Elimina las relaciones si el usuario principal es eliminado
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["usuarioAmigo"], // Clave foránea que actúa como usuario amigo
            onDelete = ForeignKey.CASCADE // Elimina las relaciones si el usuario amigo es eliminado
        )
    ]
)
data class Amigos(
    @PrimaryKey(autoGenerate = true) val IdAmigo: Int = 0, // Llave primaria para la tabla
    val usuarioPrincipal: Int, // Clave foránea para el usuario principal, referencia a IdUsuario
    val usuarioAmigo: Int, // Clave foránea para el usuario amigo, referencia a IdUsuario
    val fecha: String // Fecha en que se estableció la relación de amistad
)
