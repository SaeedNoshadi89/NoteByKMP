package org.sn.notebykmp

import androidx.room.Room
import org.koin.core.module.Module
import org.koin.dsl.module
import org.sn.notebykmp.data.database.AppDatabase
import org.sn.notebykmp.data.database.dbFileName
import java.io.File
import java.util.UUID

actual fun randomUUID(): String = UUID.randomUUID().toString()