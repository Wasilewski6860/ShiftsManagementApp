package com.example.newtryofappwithworkingshifts.presentation.change_shedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.use_cases.GetAllDaysUseCase
import com.example.newtryofappwithworkingshifts.domain.utils.ScreenState
import kotlinx.coroutines.launch

class ChangeSheduleViewModel(

    private val getAllDaysUseCase: GetAllDaysUseCase

): androidx.lifecycle.ViewModel() {


    private val _days = MutableLiveData<List<Day>>()
    val days: LiveData<List<Day>> = _days

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

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

}