package com.example.citaproyect.tablas
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grupos")
data class Grupo(
    @PrimaryKey(autoGenerate = true) val IdGrupo: Int = 0, // Llave primaria para la tabla
    val nombre: String, // Nombre del grupo
    val descripcion: String, // Descripción del grupo
    val imagen: String? = null // Imagen del grupo (opcional)
)