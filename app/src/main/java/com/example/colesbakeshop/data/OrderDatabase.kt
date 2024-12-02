package com.example.colesbakeshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Order::class], version = 1, exportSchema = false)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}