package com.example.newtryofappwithworkingshifts

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.data.WorkShift
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

class DateUtils {


    companion object{
        public fun getShift(user : User, target_day : Int, target_month : Int, target_year : Int) : WorkShift{
            var base_year : Int = user.base_year
            var base_month : Int = user.base_month
            var base_day : Int = user.base_day

            var current_year : Int = base_year
            var current_month : Int = base_month
            var current_day : Int = base_day

            var posAtList : Int = 0
            var listOfShifts : List<WorkShift> = user.shedule
            var currentShift : WorkShift = listOfShifts.get(0)

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


        public fun getShiftNew(user : User, target_day : Int, target_month : Int, target_year : Int) : WorkShift{
            var base_year : Int = user.base_year
            var base_month : Int = user.base_month
            var base_day : Int = user.base_day

            var current_year : Int = base_year
            var current_month : Int = base_month
            var current_day : Int = base_day

            var posAtList : Int = 0
            var listOfShifts : List<WorkShift> = user.shedule
            var currentShift : WorkShift = listOfShifts.get(0)


            var target = Date(target_year,target_month+1,target_day)
            var base = Date(base_year,base_month+1,base_day)

//            var target = Date(target_year,target_month+1,target_day)
//            var base = Date(base_year,base_month+1,base_day)

            val diff: Long = target.getTime() - base!!.getTime()
            var days = TimeUnit.MILLISECONDS.toDays(diff)

            days %= listOfShifts.size

            return listOfShifts.get(days.toInt())
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getDays(user : User, target_day : Int, target_month : Int, target_year : Int) : Int{
            var base_year : Int = user.base_year
            var base_month : Int = user.base_month
            var base_day : Int = user.base_day

            var current_year : Int = base_year
            var current_month : Int = base_month
            var current_day : Int = base_day

            var posAtList : Int = 0
            var listOfShifts : List<WorkShift> = user.shedule
            var currentShift : WorkShift = listOfShifts.get(0)





            var target = Date(target_year,target_month+1,target_day)
            var base = Date(base_year,base_month+1,base_day)

//            var target = Date(target_year,target_month+1,target_day)
//            var base = Date(base_year,base_month+1,base_day)

            val diff: Long = target.getTime() - base!!.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
//            var days = TimeUnit.MILLISECONDS.toDays(diff)


            return days.toInt()
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