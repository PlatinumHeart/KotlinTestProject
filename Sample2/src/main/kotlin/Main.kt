package org.example

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {
    println("Hello World!")
    runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            launch {
                doB()
            }
        }
        delay(3000)
        job.cancel()
        println("after cancel, delay 3000...")
        delay(3000)
        println("final")
    }
}

fun doA(context: CoroutineContext) {
    CoroutineScope(context).launch {
        val startTime = System.currentTimeMillis()
        var lastTime = startTime
        while (isActive) {
            val current = System.currentTimeMillis()
            if (current - lastTime < 1 * 1000) {
                continue
            }

            lastTime = current
            println("doA $current")
        }
    }
}

suspend fun doB() = withContext(Dispatchers.IO) {
    val startTime = System.currentTimeMillis()
    var lastTime = startTime
    while (isActive) {
        val current = System.currentTimeMillis()
        if (current - lastTime < 1 * 1000) {
            continue
        }

        lastTime = current
        println("doB $current")
    }
}

fun doC() {
    CoroutineScope(Dispatchers.IO).launch {
        val startTime = System.currentTimeMillis()
        var lastTime = startTime
        while (isActive) {
            val current = System.currentTimeMillis()
            if (current - lastTime < 1 * 1000) {
                continue
            }

            lastTime = current
            println("doC $current")
        }
    }
}