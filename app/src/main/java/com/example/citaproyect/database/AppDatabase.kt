package com.example.citaproyect.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.citaproyect.dao.ServiceDao
import com.example.citaproyect.tablas.Evento
import com.example.citaproyect.tablas.Usuario
import com.example.citaproyect.tablas.Amigos
import com.example.citaproyect.tablas.Encuesta
import com.example.citaproyect.tablas.Pregunta
import com.example.citaproyect.tablas.Opcion
import com.example.citaproyect.tablas.Respuesta
import com.example.citaproyect.tablas.MensajeER
import com.example.citaproyect.tablas.MiembroGrupo
import com.example.citaproyect.tablas.Grupo
import com.example.citaproyect.tablas.MensajeG


@Database(
    entities = [
        Usuario::class,
        Evento::class,
        Encuesta::class,
        Pregunta::class,
        Opcion::class,
        Respuesta::class,
        MensajeER::class,
        Amigos::class,
        Grupo::class,
        MensajeG::class,
        MiembroGrupo::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ServiceDao(): ServiceDao
}
