package com.example.challenge4.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.challenge4.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : NoteEntity)

    @Delete
    suspend fun delete(note : NoteEntity)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Update
    suspend fun update(note: NoteEntity)
}