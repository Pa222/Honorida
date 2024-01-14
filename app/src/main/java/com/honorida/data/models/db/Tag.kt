package com.honorida.data.models.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Tags",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("bookId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = arrayOf("bookId")
        )
    ]
)
data class Tag(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val bookId: Int = 0
)
