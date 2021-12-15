package day12

import com.google.common.truth.Truth
import readInput

fun main() {

    fun part1(input: List<String>): Int {

        val system = System(input)

        return system.getAllRoutes().count { path -> path.any { it.isSmall } }
    }

    fun part2(input: List<String>): Int {
        val system = System(input, simple = false)

        return system.getAllAdvancedRoutes().size
    }

    val testInput = readInput("Day12_test")
    //Truth.assertThat(part1(testInput)).isEqualTo(0)
    Truth.assertThat(part2(testInput)).isEqualTo(36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
