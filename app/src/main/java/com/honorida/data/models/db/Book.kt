package com.honorida.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val fileUrl: String,
    val coverImage: ByteArray?,
    val language: String?,
    val publishers: String?,
    val authors: String?,
    val description: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (title != other.title) return false
        if (fileUrl != other.fileUrl) return false
        if (coverImage != null) {
            if (other.coverImage == null) return false
            if (!coverImage.contentEquals(other.coverImage)) return false
        } else if (other.coverImage != null) return false
        if (language != other.language) return false
        if (publishers != other.publishers) return false
        if (authors != other.authors) return false
        return description == other.description
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + fileUrl.hashCode()
        result = 31 * result + (coverImage?.contentHashCode() ?: 0)
        result = 31 * result + (language?.hashCode() ?: 0)
        result = 31 * result + (publishers?.hashCode() ?: 0)
        result = 31 * result + (authors?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

}