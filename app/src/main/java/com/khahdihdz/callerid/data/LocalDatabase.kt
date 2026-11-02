package com.khahdihdz.callerid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhoneNumberEntity::class], version = 1, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun phoneDao(): PhoneDao
    companion object {
        @Volatile private var instance: LocalDatabase? = null
        fun get(context: Context): LocalDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, "callerid.db")
                .build().also { instance = it }
        }
    }
}
