package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val IdUsuario: Int = 0,
    val nombre: String,
    val email: String,
    val descripcion: String? = null, // Puede ser nulo
    val foto: String? = null         // Puede ser nulo
)