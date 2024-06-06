package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(InternalCoroutinesApi::class)
fun main() {
    println("Hello World!")
    runBlocking {
        CoroutineScope(Dispatchers.IO).async {
            flowOf(1, 2, 3, 4)
                .flowTest {
                    println("======== transform ========")
                    it.times(it).also { transformed ->
                        println("transform: $transformed, thread: ${Thread.currentThread()}")
                    }
                }
                .collect {
                    println("======== collect ========")
                    println("collect: $it, thread: ${Thread.currentThread()}")
                }
        }.await()
    }
}

fun <T, R> Flow<T>.flowTest(transform: (T) -> R) = flow<R> {
    this@flowTest.collect { value ->
        println("======== flowTest ========")
        println("flowTest: $value, thread: ${Thread.currentThread()}")
        val transformed = transform(value)
        emit(transformed)
    }
}

fun <T> flowOf(vararg elements: T): Flow<T> = object : Flow<T> {
    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) {
        for (element in elements) {
            println("======== emit ========")
            println("emit: $element, thread: ${Thread.currentThread()}")
            collector.emit(element)
        }
    }
}
