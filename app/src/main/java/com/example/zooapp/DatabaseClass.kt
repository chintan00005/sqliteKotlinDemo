package com.example.zooapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DatabaseClass {

    val tabelName = "notes_table"
    val dbName = "notes"
    val colId="id"
    val colTitle="title"
    val colDesc="desc"
    val dbVersion=1
    val createTable = "CREATE TABLE IF NOT EXISTs "+tabelName+" ("+colId+" integer primary key, "+
            colTitle+" text, "+colDesc+" text) ";
    val updateTable = "drop table if exists "+tabelName
    var db : SQLiteDatabase? = null;

    constructor(context:Context)
    {
        val d = sqlDatabase(context)
        db = d.writableDatabase
    }

    inner class sqlDatabase:SQLiteOpenHelper{

        var context:Context
        constructor(context: Context):super(context,dbName,null,dbVersion){
          this.context = context
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(createTable)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
          p0!!.execSQL(updateTable)
        }
    }

    fun insertData(values:ContentValues):Long{
        val ID = db!!.insert(tabelName,"",values)
        return  ID;
    }

    fun loadData(projectionList:Array<String>,selection:String,selectionArgs:Array<String>,order:String):Cursor
    {
        val sqlQuery = SQLiteQueryBuilder();
        sqlQuery.tables = tabelName

        val cursor = sqlQuery.query(db,projectionList,selection,selectionArgs,null,null,order)
        return  cursor;
    }


    fun deleteData(selections:String,selectionArgs:Array<String?>):Int{
        val ID = db!!.delete(tabelName,selections,selectionArgs)
        return  ID

    }

    fun updateData(values:ContentValues,selections:String,selectionArgs:Array<String>):Int
    {
        val Id = db!!.update(tabelName,values,selections,selectionArgs)
        return  Id
    }
}