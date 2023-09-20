package com.example.newtryofappwithworkingshifts.data.repository

import com.example.newtryofappwithworkingshifts.data.db.SheduleStorage
import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.repositories.SheduleRepository

class SheduleRepositoryImpl(private val sheduleStorage: SheduleStorage) : SheduleRepository {




    private fun mapToData(day: Day): DayDto {
        with(day) {
            return DayDto(
                id = id,
                name = name,
                description = description,
                index = index,
                color = color
            )
        }
    }

    private fun mapToDomain(dayDto: DayDto): Day {
        with(dayDto) {
            return Day(
                id = id,
                name = name,
                description = description,
                index = index,
                color = color
            )
        }
    }


    override suspend fun addDay(day: Day) {
        val mappedCat = mapToData(day)
        sheduleStorage.addDay(mappedCat)
    }

    override suspend fun editDay(day: Day) {
        val mappedCat = mapToData(day)
        sheduleStorage.editDay(mappedCat)
    }

    override suspend fun deleteDay(day: Day) {
        val mappedCat = mapToData(day)
        sheduleStorage.deleteDay(mappedCat)
    }

    override suspend fun getAllDays(): List<Day> {
        val resultFromData = sheduleStorage.getAllDays()
        return resultFromData.map { CatDto ->
            mapToDomain(CatDto)
        }
    }

    override suspend fun getDay(id: Int): Day {
        val resultFromData = sheduleStorage.getDay(id)
        return mapToDomain(resultFromData)
    }


}