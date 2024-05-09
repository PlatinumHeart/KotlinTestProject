package org.example

import com.google.gson.Gson
import model.BooleanBox

fun main() {
    println("Hello World!")
    doSome()
}

fun doSome() {
    val box = BooleanBox()
    box.isGood = true
    val json = Gson().toJson(box)
    println(json)

    val value: BooleanBox = Gson().fromJson(json, BooleanBox::class.java)
    println("--------- OUT PUT ---------")
    println(value)
}