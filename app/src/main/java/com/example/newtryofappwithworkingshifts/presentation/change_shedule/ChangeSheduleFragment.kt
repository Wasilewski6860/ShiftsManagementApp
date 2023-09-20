package com.example.newtryofappwithworkingshifts.presentation.change_shedule


import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.newtryofappwithworkingshifts.R
import com.example.newtryofappwithworkingshifts.databinding.FragmentChangeSheduleBinding
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.domain.utils.DateUtils
import com.example.newtryofappwithworkingshifts.domain.utils.ScreenState
import com.example.newtryofappwithworkingshifts.presentation.calendar.CalendarFragmentDirections
import com.example.newtryofappwithworkingshifts.presentation.change_shedule.adapter.ShiftAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangeSheduleFragment : androidx.fragment.app.Fragment()  {

    private var _binding: FragmentChangeSheduleBinding?  = null

    private val binding get() = _binding!!
    private val viewModel: ChangeSheduleViewModel by viewModel()

    private val sharedPreferences: SharedPreferences by inject()
    private val sharedPreferencesEditor: SharedPreferences.Editor by inject()
    private lateinit var shiftsAdapter: ShiftAdapter
    private  var base_year : Int = 0
    private  var base_month : Int = 0
    private  var base_day : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeSheduleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true);

        base_year = sharedPreferences.getInt("BASE_YEAR", 2023)
        base_month = sharedPreferences.getInt("BASE_MONTH", 9)
        base_day = sharedPreferences.getInt("BASE_DAY", 11)
        bindVievs(base_year, base_month, base_day)
        viewModel.fetchDays()

        viewModel.days.observe(viewLifecycleOwner) { productsList ->
            shiftsAdapter.submitList(productsList)
        }

        binding.apply{
            changeStartDateButton.setOnClickListener{
                val action = ChangeSheduleFragmentDirections.actionChangeSheduleFragmentToSetStartDateFragment()
                findNavController().navigate(action)
            }
            addShiftButton.setOnClickListener{
                val action = ChangeSheduleFragmentDirections.actionChangeSheduleFragmentToAddShiftFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->

            when (screenState) {
                ScreenState.Empty -> showEmptyScreen()
                else -> {}
            }
        }

        initRcView()
    }

    fun bindVievs(base_year : Int,base_month : Int, base_day : Int){
        binding.startDateView.text = base_day.toString()+"."+base_month.toString()+"."+base_year.toString()
    }

    private fun initRcView() {
        shiftsAdapter =
            ShiftAdapter(requireContext(), object : ShiftAdapter.ShiftActionListener {
                override fun onClickItem(day : Day) {
                    val action =
                        ChangeSheduleFragmentDirections.actionChangeSheduleFragmentToChangeShiftFragment(
                            day.id
                        )
                    findNavController().navigate(action)
                }
            })
        binding.rcViewListOfShifts.adapter = shiftsAdapter
        binding.rcViewListOfShifts.setHasFixedSize(true)
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