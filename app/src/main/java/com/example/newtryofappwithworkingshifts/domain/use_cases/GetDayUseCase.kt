package com.example.newtryofappwithworkingshifts.domain.use_cases

import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.repositories.SheduleRepository

class GetDayUseCase(private val sheduleRepository: SheduleRepository) {

    suspend fun execute(id : Int) = sheduleRepository.getDay(id)
}