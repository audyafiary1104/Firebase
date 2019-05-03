package com.example.firebase

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

import android.widget.ListView
import com.example.firebase.AdapterC.Adapter
import com.example.firebase.Add_data_Act.Users
import com.google.firebase.database.*

class ShowData : AppCompatActivity() {
    lateinit var refDb : DatabaseReference
    lateinit var list: MutableList<Users>
    lateinit var listView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_data)
        refDb = FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.listView)
        refDb.addValueEventListener(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                 }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0!!.exists())
                        list.clear()
                    for (h in p0.children){
                        val user = h.getValue(
                            Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(this@ShowData,R.layout.show_data,list)
                    listView.adapter = adapter
                }
            })
    }
}