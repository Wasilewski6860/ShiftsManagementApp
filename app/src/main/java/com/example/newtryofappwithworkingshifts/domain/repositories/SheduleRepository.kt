package com.example.newtryofappwithworkingshifts.domain.repositories

import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto
import com.example.newtryofappwithworkingshifts.domain.model.Day

interface SheduleRepository {

    suspend fun addDay(day: Day)
    suspend fun editDay(day: Day)
    suspend fun deleteDay(day: Day)
    suspend fun getAllDays(): List<Day>
    suspend fun getDay(id: Int): Day

}