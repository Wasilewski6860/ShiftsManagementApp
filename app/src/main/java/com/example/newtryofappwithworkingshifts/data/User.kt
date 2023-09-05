package com.example.newtryofappwithworkingshifts.data

data class User(
    var name : String = "Test",
    var base_year : Int = 0,
    var base_month : Int = 0,
    var base_day : Int = 0,
    var shedule : MutableList<WorkShift> = mutableListOf<WorkShift>()
)
