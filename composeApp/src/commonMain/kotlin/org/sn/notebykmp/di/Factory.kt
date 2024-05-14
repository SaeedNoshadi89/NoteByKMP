package org.sn.notebykmp.di

import androidx.room.RoomDatabase
import org.sn.notebykmp.data.database.AppDatabase

expect class Factory() {
    fun createRoomDatabase(): RoomDatabase.Builder<AppDatabase>
}
