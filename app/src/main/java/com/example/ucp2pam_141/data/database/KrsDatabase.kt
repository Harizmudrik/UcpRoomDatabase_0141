package com.example.ucp2pam_141.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dosen::class, Matakuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase(){

    abstract fun matakuliahDao(): MatakuliahDao
    abstract fun dosenDao(): DosenDao

    companion object {
        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    KrsDatabase::class.java,
                    "KrsDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}