package com.example.zooapp

class Notes{
    var noteId:String?=null
    var noteTitle:String?=null
    var noteDesc:String?=null
    constructor(noteId:String,noteTitle:String,noteDesc:String){
        this.noteId = noteId
        this.noteTitle = noteTitle
        this.noteDesc = noteDesc
    }
}