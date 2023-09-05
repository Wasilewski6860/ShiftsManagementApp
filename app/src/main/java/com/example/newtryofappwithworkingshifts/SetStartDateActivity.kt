package com.example.newtryofappwithworkingshifts

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.databinding.ActivitySetStartDateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class SetStartDateActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySetStartDateBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var user : User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetStartDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        database.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()){
                user  = it.getValue(User :: class.java)
            }
        }

        binding.calendarConfirm.setOnDateChangeListener {
                calendarView, year, month, day ->
            val newUser : User? = user
            user?.base_year = year
            user?.base_month = month
            user?.base_day = day
            var ref = database.child(firebaseAuth?.currentUser?.uid!!)
            ref.setValue(newUser)
            var intent = Intent(this,ChangeShedule :: class.java)
            startActivity(intent)
        }

    }


}