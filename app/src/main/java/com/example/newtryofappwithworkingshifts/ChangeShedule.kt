package com.example.newtryofappwithworkingshifts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtryofappwithworkingshifts.adapter.ShiftAdapter
import com.example.newtryofappwithworkingshifts.data.User
import com.example.newtryofappwithworkingshifts.data.WorkShift
import com.example.newtryofappwithworkingshifts.databinding.ActivityChangeSheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChangeShedule : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var user : User? = null
    private lateinit var binding: ActivityChangeSheduleBinding
    lateinit var adapter: ShiftAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeSheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.apply{
            changeStartDateButton.setOnClickListener{
                val intent = Intent(this@ChangeShedule, SetStartDateActivity::class.java)
                startActivity(intent)
            }
            addShiftButton.setOnClickListener{
                val intent = Intent(this@ChangeShedule, AddShiftActivity::class.java)
                startActivity(intent)
            }
        }

        initRcView()
        initToolbar()
        auth()
    }

    private fun initRcView() = with(binding){
        rcViewListOfShifts.layoutManager = LinearLayoutManager(this@ChangeShedule)
        rcViewListOfShifts.setHasFixedSize(true)
        adapter = ShiftAdapter()
        adapter.setOnClickListener(object :
            ShiftAdapter.OnClickListener {
            override fun onClick(position: Int, model: WorkShift) {
                val intent = Intent(this@ChangeShedule, ChangeShiftActivity::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra("id", position.toString())
                startActivity(intent)
                Toast.makeText(this@ChangeShedule, "YOU CLICKED, YEA!!!", Toast.LENGTH_SHORT).show()
            }
        })
        rcViewListOfShifts.adapter = adapter
    }

    private fun auth() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                onChangeListener(database.child(firebaseAuth?.currentUser?.uid!!).child("shedule"))
            }
        }.addOnFailureListener {
            Toast.makeText(this@ChangeShedule, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<WorkShift>()
                for(s in snapshot.children){
                    val shift = s.getValue(WorkShift::class.java)
                    Toast.makeText(this@ChangeShedule, "Reading from base", Toast.LENGTH_SHORT).show()
                    if(shift != null)
                        list.add(shift)
                }
                adapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Изменение графика"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                var intent = Intent(this,CalendarActivity :: class.java)
                startActivity(intent)
            }


        }
        return true
    }
}