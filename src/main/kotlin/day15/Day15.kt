package day15

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import readInput
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {

    fun part1(input: List<String>): Int {
        val cave = Cave(input, false)
        val path = cave.shortestPath()
        return cave.calculateRisk(path)
    }

    fun part2(input: List<String>): Int {
        val cave = Cave(input, true)
        val path = cave.shortestPath()
        return cave.calculateRisk(path)
    }

    val testInput = readInput("Day15_test")
    Truth.assertThat(part1(testInput)).isEqualTo(40)
    Truth.assertThat(part2(testInput)).isEqualTo(315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
