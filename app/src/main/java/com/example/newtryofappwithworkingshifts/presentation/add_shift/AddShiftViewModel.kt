package com.example.newtryofappwithworkingshifts.presentation.add_shift


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.use_cases.AddDayUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetAllDaysUseCase
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetDayUseCase
import com.example.newtryofappwithworkingshifts.domain.utils.ScreenState
import kotlinx.coroutines.launch

class AddShiftViewModel(
    private val addDayUseCase: AddDayUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getAllDaysUseCase: GetAllDaysUseCase
) : ViewModel() {

    private val _days = MutableLiveData<List<Day>>()
    val days: LiveData<List<Day>> = _days

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private fun insertNewDay(day : Day) {
        viewModelScope.launch {
            addDayUseCase.execute(day)
        }
    }

    fun addNewDay(
        name: String,
        description: String,
        color : Int
    ) {
        val newDay = Day(
            id = 0,
            name = name,
            description = description,
            index = days.value?.size ?: 0,
            color = color
        )
        insertNewDay(newDay)
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


}