package org.example

import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

fun main() {
    println("Hello World!")
    runBlocking {
        println("with time out: E")
        val res = withTimeoutOrNull(5 * 1000) {
            doSome()
        }
        println("with time out: $res X")
    }
}

private suspend fun doSome() = suspendCancellableCoroutine<Unit> { continuation ->
    getSomeByApi {
        continuation.resume(Unit) {
            Random.nextInt()
        }
    }
}

fun getSomeByApi(callback: () -> Unit) {
    var startTime = System.currentTimeMillis()
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            val now = System.currentTimeMillis()
            if (now - startTime < 1000) {
                continue
            }

            startTime = now
            println("do some at $now ...")
        }
    }
}