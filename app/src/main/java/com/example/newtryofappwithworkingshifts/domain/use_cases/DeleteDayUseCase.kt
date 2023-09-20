package com.example.newtryofappwithworkingshifts.domain.use_cases

import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.repositories.SheduleRepository

class DeleteDayUseCase(private val sheduleRepository: SheduleRepository) {

    suspend fun execute(day: Day) = sheduleRepository.deleteDay(day)
}