package com.example.challenge4.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class NoteEntity(
    @ColumnInfo(name = "title")
    val noteTitle :String,

    @ColumnInfo(name = "description")
    val noteDescription : String,

    @ColumnInfo(name = "timestamp")
    val timeStamp: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}