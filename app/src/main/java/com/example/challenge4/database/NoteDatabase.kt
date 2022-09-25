package com.example.challenge4.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challenge4.dao.NoteDao
import com.example.challenge4.entity.NoteEntity

@Database(entities = arrayOf(NoteEntity::class), version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}