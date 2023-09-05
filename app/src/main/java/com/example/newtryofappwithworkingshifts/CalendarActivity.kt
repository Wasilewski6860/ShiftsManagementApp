package com.example.newtryofappwithworkingshifts

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.data.WorkShift
import com.example.newtryofappwithworkingshifts.databinding.ActivityCalendarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate


class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val selectedDate: LocalDate? = null
    private  var base_year : Int = 0
    private  var base_month : Int = 0
    private  var base_day : Int = 0
    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.calendarView.setOnDateChangeListener {
                calendarView, year, month, day ->

            var shift : WorkShift = DateUtils.getShift(
                user!!,
                day.toInt(),
                month.toInt(),
                year.toInt())

            binding.apply {
                shiftView.text = "Смена " + " "+shift.name.toString()
                selectedDateCalendarView.text = day.toString()+"."+month.toString()+"."+year.toString()
//                Toast.makeText(this@CalendarActivity, "Days "+days.toString(), Toast.LENGTH_SHORT).show()
            }
            supportActionBar?.setBackgroundDrawable( ColorDrawable(shift.color));

            binding.changeShiftsButton.setBackgroundColor(shift.color)

        }
        binding.changeShiftsButton.setOnClickListener {
            var intent = Intent(this,ChangeShedule :: class.java)
            startActivity(intent)
        }

        initToolbar()
        auth()
    }


    private fun auth() {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                if (user?.shedule?.size == 0){
                    var intent = Intent(this,AddShiftActivity :: class.java)
                    startActivity(intent)
                }
            } else {
                user = User(
                    getIntent().getStringExtra("name")!!,base_year,base_month,base_day, mutableListOf<WorkShift>()
                )
                database.child(firebaseAuth?.currentUser?.uid!!).setValue(user)
                    .addOnSuccessListener {
//                        Toast.makeText(this@CalendarActivity, "Successfully Saved", Toast.LENGTH_SHORT)
//                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(this@CalendarActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                var intent = Intent(this,AddShiftActivity :: class.java)
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(this@CalendarActivity, "Failed", Toast.LENGTH_SHORT).show()
        }

    }


    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Календарь"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                firebaseAuth.signOut()
                var intent = Intent(this,SignInActivity :: class.java)
                startActivity(intent)
            }


        }
        return true
    }

}