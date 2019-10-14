package com.example.zooapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_note.*

var db: DatabaseClass? = null;

class AddNote: AppCompatActivity(){
    var id = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)
        db = DatabaseClass(this)

        if(intent.extras!=null)
        {

            id = intent.extras!!.getInt("id",-1)
            if(id!=-1)
            {
                edTitle.setText(intent.extras!!.getString("title"))
                edDescription.setText(intent.extras!!.getString("desc"))
            }

        }


    }

    fun onSubmit (view: View){
        val values = ContentValues()
        values.put("title",edTitle.text.toString())
        values.put("desc",edDescription.text.toString())
        if(id!=-1)
        {
            val selections="id=?"
            val selectionArgs= arrayOf(id.toString())
            Log.e("Updated",""+db!!.updateData(values,selections,selectionArgs))
        }
        else
        {
            Log.e("Inserted",""+db!!.insertData(values))
        }

        finish()


    }
}