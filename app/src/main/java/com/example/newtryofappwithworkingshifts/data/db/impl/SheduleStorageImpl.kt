package com.example.newtryofappwithworkingshifts.data.db.impl

import com.example.newtryofappwithworkingshifts.data.db.SheduleDatabase
import com.example.newtryofappwithworkingshifts.data.db.SheduleStorage
import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto

class SheduleStorageImpl(sheduleDatabase: SheduleDatabase): SheduleStorage {

    private val sheduleDao = sheduleDatabase.dao

    override suspend fun addDay(dayDto: DayDto) = sheduleDao.addDay(dayDto)
    override suspend fun editDay(dayDto: DayDto)  = sheduleDao.editDay(dayDto)
    override suspend fun deleteDay(dayDto: DayDto) = sheduleDao.deleteDay(dayDto)
    override suspend fun getAllDays(): List<DayDto> = sheduleDao.getAllDays()
    override suspend fun getDay(id: Int): DayDto = sheduleDao.getDay(id)

}