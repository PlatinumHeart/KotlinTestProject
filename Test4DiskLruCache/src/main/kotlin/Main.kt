package org.example

import com.jakewharton.disklrucache.DiskLruCache
import kotlinx.coroutines.*
import java.io.File
import kotlin.random.Random

const val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main() {
    println("Hello World!")

    val directory = File("")
    val cacheDirectoryPath = directory.absolutePath + File.separator + "Sample6" + File.separator + "cache"
    println("cacheDirectory is: $cacheDirectoryPath")

    /**
     * Value count decides the max index the editor could set to.
     * eg: If the value count is 1, the value stored by the editor with index 0 will be clean but with other index
     *     will be dirty.
     *
     * Value count decides the max index the snapshot could retrieve.
     * eg: If the value count is 1, the snapshot could only retrieve the value by index 0, and another index would
     *     throw an exception.
     */
    val cache = DiskLruCache.open(File(cacheDirectoryPath), 1, 3, 200 * 1024 * 1024)

    runBlocking {
        println("Writing ---- E")
        val defs = mutableListOf<Deferred<Unit>>()
        (0..10).forEach {
            val def = async {
                withContext(Dispatchers.IO) {
                    println("Commit key $it E")
                    val editor = cache.edit(it.toString())
                    editor.set(0, randomString())
                    editor.set(1, randomString())
                    editor.set(2, randomString())
                    editor.commit()
                    println("Commit key $it X")
                }
            }
            defs.add(def)
        }
        defs.awaitAll()
        println("Writing ---- X")

        println("Reading ---- E")
        (0 .. 10).forEach {
            async {
                withContext(Dispatchers.IO) {
                    // The snapshot would be null if the stored value decided by the certain key doesn't exist.
                    val snapshot = cache.get(it.toString())

                    val length0 = snapshot.getLength(0)
                    val value0 = snapshot.getString(0)
                    println("Snapshot key $it index: 0 length: $length0 value: $value0")

                    val length1 = snapshot.getLength(1)
                    val value1 = snapshot.getString(1)
                    println("Snapshot key $it index: 1 length: $length1 value: $value1")

                    val length2 = snapshot.getLength(2)
                    val value2 = snapshot.getString(2)
                    println("Snapshot key $it index: 2 length: $length2 value: $value2")
                }
            }.await()
        }
        println("Reading ---- X")
    }
}

private fun randomString(): String = (1..8)
    .map { ALPHABET[Random.nextInt(ALPHABET.length)] }
    .joinToString("")