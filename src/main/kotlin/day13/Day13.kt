package day13

import com.google.common.truth.Truth
import print
import readInput

fun main() {
    fun part1(input: List<String>): Int {

        val paper = Paper(input)
        paper.fold()

        return paper.area.toList().flatMap { it.toList() }.count { it }
    }

    fun part2(input: List<String>): Int {

        val paper = Paper(input)
        while (paper.hasFoldsLeft()) {
            paper.fold()
        }

        paper.area.print()

        return 0
    }

    val testInput = readInput("Day13_test")
    Truth.assertThat(part1(testInput)).isEqualTo(17)
    Truth.assertThat(part2(testInput)).isEqualTo(0)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
