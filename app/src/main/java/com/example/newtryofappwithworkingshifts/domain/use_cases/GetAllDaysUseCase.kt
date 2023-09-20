package com.example.newtryofappwithworkingshifts.domain.use_cases

import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.repositories.SheduleRepository

class GetAllDaysUseCase(private val sheduleRepository: SheduleRepository) {

    suspend fun execute() = sheduleRepository.getAllDays()
}