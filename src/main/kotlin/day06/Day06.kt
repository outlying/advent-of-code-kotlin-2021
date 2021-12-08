package day06

import readInput

fun main() {

    fun parseInput(input: List<String>): List<Int> = input[0].split(',').map { it.toInt() }

    fun cycles(fishes: List<Int>, days: Int): Long {
        var processedFishes = fishes.groupBy { it }.mapValues { it.value.count().toLong() }.toMutableMap()

        for(day in 0 until days) {
            processedFishes = processedFishes.mapKeys { it.key - 1 }.toMutableMap()

            val newFishes = processedFishes[-1]
            if(newFishes != null) {
                processedFishes.remove(-1)
                processedFishes[6] = newFishes + processedFishes.getOrDefault(6, 0)
                processedFishes[8] = newFishes
            }
        }

        return processedFishes.map { it.value }.sum()
    }

    fun part1(input: List<String>): Long {
        return cycles(parseInput(input), 80)
    }

    fun part2(input: List<String>): Long {
        return cycles(parseInput(input), 256)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
