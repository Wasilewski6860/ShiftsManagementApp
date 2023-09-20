package com.example.newtryofappwithworkingshifts.domain.utils

sealed interface ScreenState{
    object Loading: ScreenState
    object Content: ScreenState
    object Error: ScreenState
    object Empty: ScreenState
    object NoAccess : ScreenState
}