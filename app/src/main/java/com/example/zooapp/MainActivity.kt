package com.example.zooapp

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_note_data.view.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var db: DatabaseClass? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // loadData(arrayOf("%"),"title like ?","id");


    }

    override fun onResume() {
        super.onResume()
        loadData(arrayOf("%"),"title like ?","id desc");
    }
    fun loadData (filter:Array<String>,query:String,order:String){
        db = DatabaseClass(this)
        val projectionList = arrayOf<String>("id","title","desc")
        val cursor = db!!.loadData(projectionList,query,filter,order)
        if(cursor.moveToFirst())
        {
            val listData = LinkedList<Notes>()
            do{

                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val desc = cursor.getString(cursor.getColumnIndex("desc"))


                listData.push(Notes(id.toString(),title,desc))

            }while (cursor.moveToNext())



            listView.adapter = AdapterClass(listData,this,db!!)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.btnAdd->{
                val intent = Intent(this,AddNote::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class AdapterClass : BaseAdapter {

        var listData = LinkedList<Notes>();
        var context: Context;
        var db:DatabaseClass?=null

        constructor(listData: LinkedList<Notes>, context: Context, db:DatabaseClass) : super() {
            this.listData = listData
            this.context = context
            this.db = db
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).
                    inflate(R.layout.single_note_data, null)

            val data = listData[p0]
            myView.tvTitle.text = data.noteTitle
            myView.tvDescription.text = data.noteDesc

            myView.ivDelete.setOnClickListener {

                db!!.deleteData("id like ?", arrayOf(data.noteId))
               loadData(arrayOf("%"),"title like ?","id asc");
             }

            myView.ivEdit.setOnClickListener {
                val intent = Intent(context,AddNote::class.java)
                intent.putExtra("id",data.noteId!!.toInt())
                intent.putExtra("title",data.noteTitle)
                intent.putExtra("desc",data.noteDesc)
                startActivity(intent)
            }

            return  myView
        }

        override fun getItem(p0: Int): Any {
            return listData[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listData.size
        }

    }

}