package com.example.newtryofappwithworkingshifts.presentation.change_shift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.use_cases.AddDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.DeleteDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.EditDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetAllDaysUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetDayUseCase
import com.example.newtryofappwithworkingshifts.domain.utils.ScreenState
import kotlinx.coroutines.launch

class ChangeShiftViewModel(
    private val editDayUseCase: EditDayUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getAllDaysUseCase: GetAllDaysUseCase
) : ViewModel() {

    private val _days = MutableLiveData<List<Day>>()
    val days: LiveData<List<Day>> = _days

    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day> = _day

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private fun updateDay(day : Day) {
        viewModelScope.launch {
            editDayUseCase.execute(day)
        }
    }

    fun editDay(
        id : Int,
        name: String,
        description: String,
        index : Int,
        color : Int
    ) {
        val newDay = Day(
            id = id,
            name = name,
            description = description,
            index = index,
            color = color
        )
        updateDay(newDay)
    }

    fun deleteDay() {
        viewModelScope.launch {
            day.value?.let { deleteDayUseCase.execute(it) }
        }
    }

    fun fetchDays() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                _days.value = getAllDaysUseCase.execute()
                _screenState.value =
                    if (days.value?.isEmpty() == true) ScreenState.Empty else ScreenState.Content

            } catch (e: Exception) {
                _screenState.value = ScreenState.Error
            }
        }
    }


    fun isInputIsValid(
        name: String
    ) = (name.isNotBlank())

    fun getDay(id: Int) {
        viewModelScope.launch {
            _day.value = getDayUseCase.execute(id)
        }
    }


}