package com.example.citaproyect.database
import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var appDatabase: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).build()
        }
        return appDatabase!!
    }
}