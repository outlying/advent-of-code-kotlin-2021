package day14

import com.google.common.truth.Truth
import println
import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun main() {

    fun part1(input: List<String>): Int {

        val rules = Rules(input)

        repeat(10) { rules.step() }

        val grouped = rules.polymer.toList()
            .groupBy { it }
            .mapValues { it.value.size }

        val max = grouped.maxOf { it.value }
        val min = grouped.minOf { it.value }

        return max - min
    }

    fun part2(input: List<String>): Long {

        return Rules(input).check(40)

        val rules = Rules(input)

        measureTime {
            repeat(15) { rules.step() }
        }.println()

        val grouped = rules.polymer.toList()
            .groupBy { it }
            .mapValues { it.value.size }

        val max = grouped.maxOf { it.value }
        val min = grouped.minOf { it.value }

        return (max - min).toLong()
    }

    val testInput = readInput("Day14_test")
    Truth.assertThat(part1(testInput)).isEqualTo(1588)
    Truth.assertThat(part2(testInput)).isEqualTo(2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
