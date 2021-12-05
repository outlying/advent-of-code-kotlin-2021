import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


fun <T> List<List<T>>.transpose(): List<List<T>> {
    val row = size
    val column = get(0).size

    // Transpose the matrix
    val transpose = MutableList(column) { MutableList(row) { null as T } }
    for (i in 0 until row) {
        for (j in 0 until column) {
            transpose[j][i] = get(i)[j]
        }
    }
    return transpose
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
