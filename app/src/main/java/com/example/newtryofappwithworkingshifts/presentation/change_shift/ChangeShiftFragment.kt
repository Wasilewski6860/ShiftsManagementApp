package com.example.newtryofappwithworkingshifts.presentation.change_shift


import android.content.Intent
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
import com.example.newtryofappwithworkingshifts.domain.model.Day
import com.example.newtryofappwithworkingshifts.presentation.add_shift.AddShiftFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import yuku.ambilwarna.AmbilWarnaDialog

class ChangeShiftFragment : Fragment() {

    private var _binding: FragmentChangeShiftBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangeShiftViewModel by viewModel()
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
        _binding = FragmentChangeShiftBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedColor = ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary)
        viewModel.fetchDays()

        viewModel.getDay(dayId)
        viewModel.day.observe(viewLifecycleOwner) {
            bindViews(it)
        }

        binding.changeFinishButton.setOnClickListener {
            saveDay()
        }
        binding.changeDeleteButton.setOnClickListener {
            viewModel.deleteDay()
            val action = ChangeShiftFragmentDirections.actionChangeShiftFragmentToChangeSheduleFragment()
            findNavController().navigate(action)
        }
        binding.changeSelectColorButton.setOnClickListener {
            openColorPicker()
        }

    }

    private fun isNameValid(): Boolean {
        return viewModel.isInputIsValid(
            name = binding.nameEditText.text.toString(),
        )
    }


    private fun bindViews(day : Day) {

        binding.apply {
            nameEditText.setText(day.name, TextView.BufferType.SPANNABLE)
            changePreviewView.setBackgroundColor(day.color)
            selectedColor = day.color
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

    fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this.requireContext(), selectedColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                selectedColor = color
                binding.changePreviewView.setBackgroundColor(selectedColor)
            }
        })
        colorPicker.show()
    }

    private fun saveDay() {

        var index = viewModel.day.value?.index ?: 10

        if (isNameValid()) {

            if (dayId>0){
                viewModel.editDay(
                    dayId,
                    name = binding.nameEditText.text.toString(),
                    "",
                    index,
                    selectedColor
                )
                val action = ChangeShiftFragmentDirections.actionChangeShiftFragmentToChangeSheduleFragment()
                findNavController().navigate(action)
            }

            else{
                viewModel.editDay(
                    dayId,
                    name = binding.nameEditText.text.toString(),
                    "",
                    index,
                    selectedColor
                )
                val action = ChangeShiftFragmentDirections.actionChangeShiftFragmentToChangeSheduleFragment()
                findNavController().navigate(action)
            }
        } else {
            Toast.makeText(this.requireContext(), "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
        }

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

}