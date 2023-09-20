package com.example.newtryofappwithworkingshifts.presentation.set_start_date

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newtryofappwithworkingshifts.R
import com.example.newtryofappwithworkingshifts.databinding.FragmentChangeShiftBinding
import com.example.newtryofappwithworkingshifts.databinding.FragmentSetStartDateBinding
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.presentation.add_shift.AddShiftFragmentDirections
import com.example.newtryofappwithworkingshifts.presentation.change_shift.ChangeShiftFragmentDirections
import com.example.newtryofappwithworkingshifts.presentation.change_shift.ChangeShiftViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import yuku.ambilwarna.AmbilWarnaDialog

class SetStartDateFragment : Fragment() {

    private var _binding: FragmentSetStartDateBinding? = null
    private val binding get() = _binding!!
    private var dayId = -1

    private val sharedPreferences: SharedPreferences by inject()
    private val sharedPreferencesEditor: SharedPreferences.Editor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayId = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetStartDateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.calendarConfirm.setOnDateChangeListener {
                calendarView, year, month, day ->
            writePersonalDataToSharedPref(year, month, day)
            val action = SetStartDateFragmentDirections.actionSetStartDateFragmentToChangeSheduleFragment()
            findNavController().navigate(action)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID = "id"
    }

    fun showSnackbar(text : String){
        Snackbar.make(
            requireActivity().findViewById(R.id.rootView),
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val action =
                    ChangeShiftFragmentDirections.actionChangeShiftFragmentToChangeSheduleFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }

    private fun writePersonalDataToSharedPref(year : Int, month : Int, day : Int): Boolean {

        sharedPreferences.edit()
            .putInt("YEAR", year)
            .putInt("MONTH", month)
            .putInt("DAY", day)
            .apply()

        return true
    }

}