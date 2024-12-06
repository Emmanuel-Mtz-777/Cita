package com.example.citaproyect

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Usuario::class,
        Respuesta::class,
        Pregunta::class,
        Opcion::class,
        MiembroGrupo::class,
        MensajeG::class,
        MensajeER::class,
        Grupo::class,
        Evento::class,
        Encuesta::class,
        Amigos::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ServiceDao(): ServiceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
