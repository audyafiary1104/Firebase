package com.example.firebase.AdapterC

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firebase.Add_data_Act.Users
import com.example.firebase.R
import com.example.firebase.ShowData
import com.google.firebase.database.FirebaseDatabase

class Adapter (val mCtx: Context,val layoutResId:Int,val list: List<Users>)
    :ArrayAdapter<Users>(mCtx,layoutResId,list){
    override fun getView(position: Int, convertView: View?,parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View=  layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.tv1)
        val textStatus = view.findViewById<TextView>(R.id.tv2)
        val textDelete = view.findViewById<TextView>(R.id.delete)
        val textUpdate = view.findViewById<TextView>(R.id.update)
        val user = list[position]
        textNama.text = user.nama
        textStatus.text = user.status

        textUpdate.setOnClickListener {
        showUpdateDialog(user)
        }
        textDelete.setOnClickListener {
            DeleteInfo(user)
        }
        return view
    }
    private fun DeleteInfo(user: Users){
        val progressDialog = ProgressDialog(context,R.style.AppTheme)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting..")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance()
            .getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted",Toast.LENGTH_SHORT).show()
        val intent = Intent(context,ShowData::class.java)
        context.startActivity(intent)
    }
    private fun showUpdateDialog(user: Users){
        val builder = AlertDialog.Builder(mCtx)
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_data,null)
        val textNama = view.findViewById<EditText>(R.id.inputNama)
        val textStatus = view.findViewById<EditText>(R.id.inputStatus)
        textNama.setText(user.nama)
        textStatus.setText(user.status)
        builder.setView(view)
        builder.setPositiveButton("Update"){
            dialog, which ->
            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")
            val nama = textNama.text.toString().trim()
            val status = textStatus.text.toString().trim()
            if (nama.isEmpty()){
                textNama.error = "Please enter name"
                textNama.requestFocus()
                return@setPositiveButton
            }
            if (status.isEmpty()){
                textStatus.error = "Please Enter Status"
                textStatus.requestFocus()
                return@setPositiveButton
            }
            val user = Users(user.id,nama,status)
            dbUsers.child(user.id).setValue(user)
                .addOnCompleteListener{
                    Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
                }
          }
        builder.setNegativeButton("NO"){
            dialog, which ->
        }
        val alert = builder.create()
        alert.show()
     }
}