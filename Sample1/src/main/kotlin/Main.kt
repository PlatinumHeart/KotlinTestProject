package org.example

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

fun main() {
    val superJob = SupervisorJob()
    println("superJob is: $superJob")
    val superMainScope = CoroutineScope(Dispatchers.IO)
    runBlocking {
        val job = superMainScope.launch {
            printJob("context in global scope")
            launch {
                printJob("context in child scope")
            }
            CoroutineScope(coroutineContext + EmptyCoroutineContext).launch {
                printJob("context in combine scope")
            }
            suspendCoroutine {
                println("context in suspend coroutine: ${it.context}")
                val job = it.context[Job]
                println("job in suspend coroutine is: $job")
                CoroutineScope(it.context).launch {
                    println("context start by coroutine scope, is: $coroutineContext")
                    doSome()
                }
            }
        }
        println("job is: $job")
        delay(3 * 1000)
        job.cancelAndJoin()
        println("job is cancelled.")
        println("superMainScope is: $superMainScope")
    }
}

private suspend fun doSome() {
    val startTime = System.currentTimeMillis()
    var lastTime = startTime
    while (true) {
        val current = System.currentTimeMillis()
        if (current - startTime > 4 * 1000) {
            break
        }

        if (current - lastTime < 1 * 1000) {
            continue
        }

        lastTime = current
        printJob("doSome", current)
    }

    println("start delay ....${coroutineContext.isActive}")
    delay(1000)
}

private suspend fun printJob(src: String, time: Long = 0L) {
    val msg = """
             -----------
            | $src....
            | time: $time
            | context: $coroutineContext
             -----------
        """.trimIndent()
    println(msg)
}
