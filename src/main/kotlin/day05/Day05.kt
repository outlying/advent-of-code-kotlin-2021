package day05

import print
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val area = Area(input)
        val matrix = area.buildEmpty()
        area.fillWithVerticalAndHorizontal(matrix)

        return matrix.toList().flatMap { it.toList() }.count { it >= 2 }
    }

    fun part2(input: List<String>): Int {
        val area = Area(input)
        val matrix = area.buildEmpty()
        area.fillWithAllLines(matrix)

        return matrix.toList().flatMap { it.toList() }.count { it >= 2 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
