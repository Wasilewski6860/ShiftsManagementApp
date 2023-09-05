package com.example.newtryofappwithworkingshifts


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.data.WorkShift
import com.example.newtryofappwithworkingshifts.databinding.ActivityAddShiftBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener


class AddShiftActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddShiftBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var user : User? = null
    private var list : MutableList<WorkShift> = mutableListOf<WorkShift>()

    var selectedColor : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddShiftBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        selectedColor = ContextCompat.getColor(applicationContext, com.google.android.material.R.color.design_default_color_primary)

        binding.addNewShiftButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            binding.nameEditText.getText().clear();
            if ( name.isNotEmpty() ) {
               list.add(WorkShift(name, selectedColor ))
                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }

        }
        binding.toCalendarButton.setOnClickListener {
            user?.shedule = list
            Toast.makeText(this, "len "+user?.shedule?.size, Toast.LENGTH_SHORT).show()
            var ref = database.child(firebaseAuth?.currentUser?.uid!!)
            ref.setValue(user)
            var intent = Intent(this,SetStartDateActivity :: class.java)
            startActivity(intent)
        }
        binding.selectColorButton.setOnClickListener {
            openColorPicker()
        }

        initToolbar()
        auth()
    }

    private fun auth() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                list = user!!.shedule
                Toast.makeText(this@AddShiftActivity, "You are in auth() and user "+user.toString(), Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this@AddShiftActivity, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this, selectedColor, object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                selectedColor = color
                binding.previewView.setBackgroundColor(selectedColor)
            }
        })
        colorPicker.show()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Редактор графика"
    }

}