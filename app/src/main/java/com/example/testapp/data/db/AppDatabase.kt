package com.example.testapp.data.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.data.db.dao.UserDao
import com.example.testapp.data.db.dto.UserDto

@Database(entities = [UserDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: AppDatabase
            private set

        fun init(context: Context) {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
        }
    }

}