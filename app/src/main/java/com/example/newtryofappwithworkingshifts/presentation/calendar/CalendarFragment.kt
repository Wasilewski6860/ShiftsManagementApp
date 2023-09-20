package com.example.newtryofappwithworkingshifts.presentation.calendar

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newtryofappwithworkingshifts.R
import com.example.newtryofappwithworkingshifts.databinding.FragmentCalendarBinding
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.utils.DateUtils
import com.example.newtryofappwithworkingshifts.domain.utils.ScreenState
import com.example.shiftmanagementappmvvm.presentation.calendar.CalendarViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout


class CalendarFragment : androidx.fragment.app.Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModel()

    private val sharedPreferences: SharedPreferences by inject()
    private val sharedPreferencesEditor: SharedPreferences.Editor by inject()

    private  var base_year : Int = 0
    private  var base_month : Int = 0
    private  var base_day : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true);

        base_year = sharedPreferences.getInt("YEAR", 2023)
        base_month = sharedPreferences.getInt("MONTH", 9)
        base_day = sharedPreferences.getInt("DAY", 11)

        viewModel.fetchDays()


        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->

            when (screenState) {
                ScreenState.Empty -> showEmptyScreen()
                else -> {}
            }
        }

    }

    fun bindViews(base_year : Int,base_month : Int,base_day : Int){
        with(binding){

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setOnDateChangeListener {
                calendarView, year, month, day ->

            var shift : Day = DateUtils.getShift(
                base_year,base_month,base_day,
                viewModel.days.value!!,
                day.toInt(),
                month.toInt(),
                year.toInt())

            binding.apply {
                shiftView.text = "Смена " + " "+shift.name.toString()
                selectedDateCalendarView.text = day.toString()+"."+month.toString()+"."+year.toString()
//                Toast.makeText(this@CalendarActivity, "Days "+days.toString(), Toast.LENGTH_SHORT).show()
            }

            requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout).setBackgroundDrawable( ColorDrawable(shift.color))

            binding.changeSheduleButton.setBackgroundColor(shift.color)
        }

        binding.changeSheduleButton.setOnClickListener {
//            var intent = Intent(this,ChangeShedule :: class.java)
//            startActivity(intent)
            val action = CalendarFragmentDirections.actionCalendarFragmentToChangeSheduleFragment()
            findNavController().navigate(action)
        }

    }


    private fun showEmptyScreen() {
        val action = CalendarFragmentDirections.actionCalendarFragmentToChangeSheduleFragment()
        findNavController().navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun writePersonalDataToSharedPref(): Boolean {
        sharedPreferences.edit()
            .putBoolean("KEY_IS_FIRST",true)
            .apply()
        return true
    }
}