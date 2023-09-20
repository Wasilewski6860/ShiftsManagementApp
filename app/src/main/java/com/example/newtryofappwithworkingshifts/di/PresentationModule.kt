package com.example.newtryofappwithworkingshifts.di

import com.example.newtryofappwithworkingshifts.presentation.add_shift.AddShiftViewModel
import com.example.newtryofappwithworkingshifts.presentation.change_shedule.ChangeSheduleViewModel
import com.example.newtryofappwithworkingshifts.presentation.change_shift.ChangeShiftViewModel
import com.example.shiftmanagementappmvvm.presentation.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<AddShiftViewModel>{
        AddShiftViewModel(
           addDayUseCase = get(),
            getDayUseCase = get(),
            getAllDaysUseCase = get()
        )
    }
    viewModel<CalendarViewModel>{
        CalendarViewModel(
           getAllDaysUseCase = get()
        )
    }
    viewModel<ChangeShiftViewModel>{
        ChangeShiftViewModel(
            editDayUseCase = get(),
            deleteDayUseCase = get(),
            getDayUseCase = get(),
            getAllDaysUseCase = get()
        )
    }
    viewModel<ChangeSheduleViewModel>{
        ChangeSheduleViewModel(
            getAllDaysUseCase = get()
        )
    }
}