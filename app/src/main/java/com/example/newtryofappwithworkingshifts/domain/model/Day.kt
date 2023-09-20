package com.example.newtryofappwithworkingshifts.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Day(
    val id: Int = 0,
    val name: String,
    val description: String,
    var index: Int,
    var color : Int =0
)