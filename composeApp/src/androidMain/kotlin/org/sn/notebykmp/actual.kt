package org.sn.notebykmp

import android.app.Application
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import android.content.Context
import org.koin.dsl.module
import org.sn.notebykmp.data.database.AppDatabase
import org.sn.notebykmp.data.database.dbFileName
import java.util.UUID

actual fun randomUUID(): String = UUID.randomUUID().toString()