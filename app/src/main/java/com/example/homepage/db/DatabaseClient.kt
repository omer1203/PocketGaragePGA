package com.example.homepage.db

import android.content.Context
import androidx.room.Room

object DatabaseClient {

    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "homepage_database" // Database name
                ).build()
            }
        }
        return instance!!
    }
}
