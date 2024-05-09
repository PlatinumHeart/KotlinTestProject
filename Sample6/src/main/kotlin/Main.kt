package org.example

import com.jakewharton.disklrucache.DiskLruCache
import kotlinx.coroutines.*
import java.io.File

fun main() {
    println("Hello World!")

    val directory = File("")
    val cacheDirectoryPath = directory.absolutePath + File.separator + "Sample6" + File.separator + "cache"
    println("cacheDirectory is: $cacheDirectoryPath")
    val cache = DiskLruCache.open(File(cacheDirectoryPath), 1, 1, 200 * 1024 * 1024)

    runBlocking {
        println("Writing ---- E")
        val coroutineContext = SupervisorJob()
        (0 .. 10).forEach {
            CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                val editor = cache.edit(it.toString())
                editor.set(0, "VVVVVVV")
                editor.set(1, "TTTTTTT")
                editor.commit()
            }
        }
        println("Writing ---- X")
    }
}