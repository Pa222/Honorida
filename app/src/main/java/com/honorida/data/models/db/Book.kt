package com.honorida.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
)