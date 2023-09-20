package com.example.newtryofappwithworkingshifts.di

import com.example.newtryofappwithworkingshifts.domain.use_cases.AddDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.DeleteDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.EditDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetAllDaysUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetDayUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<AddDayUseCase> { AddDayUseCase(sheduleRepository = get()) }
    factory<DeleteDayUseCase> { DeleteDayUseCase(sheduleRepository = get()) }
    factory<EditDayUseCase> { EditDayUseCase(sheduleRepository = get()) }
    factory<GetAllDaysUseCase> { GetAllDaysUseCase(sheduleRepository = get()) }
    factory<GetDayUseCase> { GetDayUseCase(sheduleRepository = get()) }


}