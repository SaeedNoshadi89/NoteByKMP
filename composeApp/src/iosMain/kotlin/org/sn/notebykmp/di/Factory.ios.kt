package org.sn.notebykmp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.sn.notebykmp.data.database.AppDatabase
import org.sn.notebykmp.data.database.dbFileName
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import org.sn.notebykmp.data.database.instantiateImpl

actual class Factory {
    actual fun createRoomDatabase(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = "${fileDirectory()}/$dbFileName"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile,
            factory = {
                AppDatabase::class.instantiateImpl()
            }
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }

}