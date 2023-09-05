package com.example.newtryofappwithworkingshifts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.newtryofappwithworkingshifts.adapter.ShiftAdapter
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.data.WorkShift
import com.example.newtryofappwithworkingshifts.databinding.ActivityAddShiftBinding
import com.example.newtryofappwithworkingshifts.databinding.ActivityChangeSheduleBinding
import com.example.newtryofappwithworkingshifts.databinding.ActivityChangeShiftBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import yuku.ambilwarna.AmbilWarnaDialog

class ChangeShiftActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeShiftBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var user : User? = null
    private var list : MutableList<WorkShift> = mutableListOf<WorkShift>()

    var selectedColor : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeShiftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        selectedColor = ContextCompat.getColor(applicationContext, com.google.android.material.R.color.design_default_color_primary)

        binding.changeFinishButton.setOnClickListener {
            val name = binding.changeNameEditText.text.toString()
            if ( name.isNotEmpty() ) {
                var workShift = WorkShift(name, selectedColor )
                var ref = database.child(firebaseAuth?.currentUser?.uid!!).child("shedule").child(getIntent().getStringExtra("id").toString())
                ref.setValue(workShift)
                var intent = Intent(this,ChangeShedule :: class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changeDeleteButton.setOnClickListener {
            auth()
            list.removeAt(getIntent().getStringExtra("id").toString().toInt())
            var ref = database.child(firebaseAuth?.currentUser?.uid!!).child("shedule")
            ref.setValue(list)
            var intent = Intent(this,ChangeShedule :: class.java)
            startActivity(intent)
        }
        binding.changeSelectColorButton.setOnClickListener {
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
            }
        }.addOnFailureListener {
            Toast.makeText(this@ChangeShiftActivity, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this, selectedColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                selectedColor = color
                binding.changePreviewView.setBackgroundColor(selectedColor)
            }
        })
        colorPicker.show()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Редактирование смены"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                var intent = Intent(this,ChangeShedule :: class.java)
                startActivity(intent)
            }


        }
        return true
    }

}