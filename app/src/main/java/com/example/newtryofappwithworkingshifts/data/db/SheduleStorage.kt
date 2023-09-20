package com.example.newtryofappwithworkingshifts.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto

interface SheduleStorage {

    suspend fun addDay(dayDto: DayDto)
    suspend fun editDay(dayDto: DayDto)
    suspend fun deleteDay(dayDto: DayDto)
    suspend fun getAllDays(): List<DayDto>
    suspend fun getDay(id: Int): DayDto

}