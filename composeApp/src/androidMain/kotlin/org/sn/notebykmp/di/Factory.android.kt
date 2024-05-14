package org.sn.notebykmp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.sn.notebykmp.AndroidApp
import org.sn.notebykmp.AndroidApp.Companion.INSTANCE
import org.sn.notebykmp.data.database.AppDatabase
import org.sn.notebykmp.data.database.dbFileName

actual class Factory {
    actual fun createRoomDatabase(): RoomDatabase.Builder<AppDatabase> {
        val app: Application = INSTANCE
        val dbFile = app.getDatabasePath(dbFileName)
        return Room.databaseBuilder<AppDatabase>(app, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
    }
}