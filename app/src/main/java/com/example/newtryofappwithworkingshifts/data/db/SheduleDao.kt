package com.example.newtryofappwithworkingshifts.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newtryofappwithworkingshifts.data.db.dto.DayDto

@Dao
interface SheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDay(dayDto: DayDto)

    @Update
    suspend fun editDay(dayDto: DayDto)

    @Delete
    suspend fun deleteDay(dayDto: DayDto)

    @Query("SELECT * FROM days ORDER BY `index` ASC")
    suspend fun getAllDays(): List<DayDto>

    @Query("SELECT * FROM days WHERE id =:id")
    suspend fun getDay(id: Int): DayDto


}