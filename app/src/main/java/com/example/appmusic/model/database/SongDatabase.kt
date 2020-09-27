package com.example.appmusic.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appmusic.model.ItemSong

@Database(entities = [ItemSong::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun getSongDao(): SongDao

    companion object {
        private var INSTANCE: SongDatabase? = null
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, SongDatabase::class.java, "ap.db"
            )
                .allowMainThreadQueries()
                .build()


        fun getInstance(context: Context): SongDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        }
    }
}