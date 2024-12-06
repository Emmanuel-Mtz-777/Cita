package com.example.citaproyect.tablas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "miembrosGrupo",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Clave foránea que hace referencia al usuario
            onDelete = ForeignKey.CASCADE // Elimina el miembro si el usuario es eliminado
        ),
        ForeignKey(
            entity = Grupo::class, // Referencia a la entidad Grupo
            parentColumns = ["IdGrupo"], // Columna primaria en Grupo
            childColumns = ["IdGrupo"], // Clave foránea que hace referencia al grupo
            onDelete = ForeignKey.CASCADE // Elimina el miembro si el grupo es eliminado
        )
    ]
)
data class MiembroGrupo(
    @PrimaryKey(autoGenerate = true) val IdMiembro: Int = 0, // Llave primaria para la tabla
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val IdGrupo: Int, // Clave foránea que hace referencia a la tabla Grupo
    val fechaIngreso: String // Fecha de ingreso del usuario al grupo
)