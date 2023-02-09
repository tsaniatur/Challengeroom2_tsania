package com.example.challengeroom2_tsania.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (
    entities = [tbbuku::class],
    version = 1
)
    abstract class dbperpustakaan: RoomDatabase() {
        abstract fun perpusDAO():DAO

        companion object{
            @Volatile private var instance : dbperpustakaan? = null
            private val LOCK = Any()

            operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
                instance ?:buildDatabase(context).also {
                    instance = it
                }
            }
            private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                dbperpustakaan::class.java,
                    "db.perpus"

            ).build()
        }
}