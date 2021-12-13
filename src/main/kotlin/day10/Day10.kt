package day10

import com.google.common.truth.Truth
import readInput
import java.util.*

fun main() {

    fun part1(input: List<String>): Int {

        val points = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )

        var sum = 0

        for (line in input) {
            try {
                Parser.parse(LinkedList(line.toList()))
            } catch (e: Parser.IllegalClosingCharException) {
                sum += points[e.incorrectChar] ?: throw IllegalStateException("Failed")
            } catch (e: Exception) {
                // do nothing
            }
        }

        return sum
    }

    fun part2(input: List<String>): Long {

        val incomplete = input
            .filter { line ->
                val openingChars = line.count { it.isOpeningChar() }
                val closingChars = line.count { it.isClosingChar() }
                openingChars != closingChars
            }

        var scores = mutableListOf<Long>()

        for (line in incomplete) {

            try {
                val result = Parser.parse(LinkedList(line.toList()))
                val addedPart = result.joinToString(separator = "").drop(line.length)

                val points = addedPart.map { when(it) {
                    ')' -> 1L
                    ']' -> 2L
                    '}' -> 3L
                    '>' -> 4L
                    else -> throw IllegalStateException("Something went wrong")
                } }

                val score = points.fold(0L) { acc, i -> (acc * 5) + i }

                scores.add(score)

            } catch (e: Parser.IllegalClosingCharException) {
                // Corrupted
            }
        }

        return scores.sorted()[scores.size / 2]
    }

    val testInput = readInput("Day10_test")
    Truth.assertThat(part1(testInput)).isEqualTo(26397)
    Truth.assertThat(part2(testInput)).isEqualTo(288957)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
