package com.example.newtryofappwithworkingshifts.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newtryofappwithworkingshifts.domain.model.Day
import java.util.Date
import java.util.concurrent.TimeUnit

class DateUtils {


    companion object{
        public fun getShift(baseYear : Int,baseMonth : Int, baseDay : Int, listOfShifts : List<Day>, target_day : Int, target_month : Int, target_year : Int) : Day{

            var current_year : Int = baseYear
            var current_month : Int = baseMonth
            var current_day : Int = baseDay

            var posAtList : Int = 0
            var currentShift : Day = listOfShifts.get(0)

            while (
                !(
                        current_year == target_year
                                &&
                                current_month == target_month
                                &&
                                (current_day == target_day+1)
                        )
            ){
                var current_month_length = getMonthLength(current_month+1,current_year)

                currentShift = listOfShifts.get(posAtList)
                posAtList++
                if (posAtList==listOfShifts.size) posAtList = 0

                current_day++
                if (current_day == current_month_length+1){
                    current_day=1
                    current_month++
                    if (current_month>target_month) break
                    if (current_month==13){
                        current_month=1
                        current_year++
                    }
                    if (current_year>target_year) break
                }
            }

            return currentShift
        }



        fun getMonthLength(month : Int, year : Int) : Int{

            when(month){
                1 -> return 31
                2 -> return if (isYearLeap(year)) 29 else 28
                3 -> return 31
                4 -> return 30
                5 -> return 31
                6 -> return 30
                7 -> return 31
                8 -> return 31
                9 -> return 30
                10 -> return 31
                11 -> return 30
                12 -> return 31
            }
            return 30
        }

        /*
        If true - February lenth is 29, else is 28
         */
        fun isYearLeap(year : Int) : Boolean{
            return (year - 2020) % 4 ==0
        }
    }




}