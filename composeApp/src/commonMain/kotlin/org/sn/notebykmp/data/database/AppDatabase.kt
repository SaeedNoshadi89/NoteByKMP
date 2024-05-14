package org.sn.notebykmp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.sn.notebykmp.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

internal const val dbFileName = "notes.db"
