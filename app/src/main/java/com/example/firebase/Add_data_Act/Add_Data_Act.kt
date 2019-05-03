package com.example.firebase.Add_data_Act

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.firebase.R
import com.example.firebase.ShowData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_data.*

class Add_Data_Act : AppCompatActivity() {
    lateinit var refDb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_data)
        refDb = FirebaseDatabase.getInstance()
            .getReference("USERS")
        btnSave.setOnClickListener {
            saveData()
            val intent = Intent(this,
                ShowData::class.java)
            startActivity(intent)
        }
    }
    private fun saveData(){
        val nama = inputName.text.toString()
        val status = inputStatus.text.toString()
        val userId = refDb.push().key.toString()
        val user = Users(userId,nama,status)
        refDb.child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this,"Successs",Toast.LENGTH_SHORT).show()
                inputName.setText("")
                inputStatus.setText("")
            }
    }
}