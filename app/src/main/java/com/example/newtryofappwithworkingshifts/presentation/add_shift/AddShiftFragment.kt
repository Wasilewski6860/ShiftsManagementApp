package com.example.newtryofappwithworkingshifts.presentation.add_shift

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newtryofappwithworkingshifts.R
import com.example.newtryofappwithworkingshifts.databinding.FragmentAddShiftBinding
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import yuku.ambilwarna.AmbilWarnaDialog

class AddShiftFragment : Fragment() {

    private var _binding: FragmentAddShiftBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddShiftViewModel by viewModel()
    private var dayId = -1
    private lateinit var day: Day

    var selectedColor : Int = 0

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
        _binding = FragmentAddShiftBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedColor = ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary)

        viewModel.fetchDays()

        binding.addNewShiftButton.setOnClickListener {
            saveDay()
        }
        binding.toCalendarButton.setOnClickListener {
            val action = AddShiftFragmentDirections.actionAddShiftFragmentToChangeSheduleFragment()
            findNavController().navigate(action)
        }
        binding.selectColorButton.setOnClickListener {
            openColorPicker()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID = "id"
    }

    private fun isNameValid() : Boolean =  viewModel.isInputIsValid(binding.nameEditText.text.toString())

    private fun saveDay() {

        if (isNameValid()) {
                viewModel.addNewDay(
                    name = binding.nameEditText.text.toString(),
                    description = "",
                    color = selectedColor
                )
            binding.nameEditText.getText()?.clear();
        }
        else Toast.makeText(this.requireContext(), "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

    }


    fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this.requireContext(), selectedColor, object :
            AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                selectedColor = color
                binding.previewView.setBackgroundColor(selectedColor)
            }
        })
        colorPicker.show()
    }

}