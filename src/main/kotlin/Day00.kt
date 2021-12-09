import com.google.common.truth.Truth
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day00_test")
    Truth.assertThat(part1(testInput)).isEqualTo(0)
    Truth.assertThat(part2(testInput)).isEqualTo(0)

    val input = readInput("Day00")
    println(part1(input))
    println(part2(input))
}
