package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.sync.Mutex

fun main() {
    println("Hello World!")
    val flow = MutableSharedFlow<Int>()

    val coroutineContext = SupervisorJob()
    val scope = CoroutineScope(coroutineContext)
    runBlocking {
        launch {
            (0..10).forEach {
                scope.launch {
                    println("vvvvvvvv ! try emit $it vvvvvvvv")
                    flow.emit(it)
                    println("vvvvvvvv ! try emited $it vvvvvvvv")
                }
            }
        }

        launch {
            println("Ready to collect.")
            flow.collect {
                println("^^^^^^^^ @@@ collect $it ^^^^^^^^")
            }
        }
    }
}