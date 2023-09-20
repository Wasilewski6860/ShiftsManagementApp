package com.example.newtryofappwithworkingshifts.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto

@Database(
    entities = [
        DayDto::class
    ],
    version = 1
)
abstract class SheduleDatabase : RoomDatabase() {

    abstract val dao: SheduleDao

    companion object {
        @Volatile
        private var INSTANCE: SheduleDatabase? = null

        fun getInstance(context: Context): SheduleDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SheduleDatabase::class.java,
                    "shedule_db"
                ) .fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}