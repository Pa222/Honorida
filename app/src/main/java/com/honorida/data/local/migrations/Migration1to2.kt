package com.honorida.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val Migration1to2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "DROP TABLE `Books`"
        )
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `Books` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "`title` TEXT NOT NULL," +
                    "`fileUrl` TEXT NOT NULL," +
                    "`coverImage` BLOB," +
                    "`language` TEXT," +
                    "`publishers` TEXT," +
                    "`authors` TEXT," +
                    "`description` TEXT" +
                ")"
        )
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS `Tags` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "`name` TEXT NOT NULL," +
                    "`bookId` INTEGER NOT NULL," +
                    "FOREIGN KEY (`bookId`) REFERENCES `Books` (`id`) ON DELETE CASCADE" +
                ")"
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_Subjects_bookId` ON `Tags` (`bookId`)"
        )
    }
}