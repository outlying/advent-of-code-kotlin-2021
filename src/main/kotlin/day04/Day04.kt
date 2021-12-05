package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return BingoGame(input).findWiningBoard()!!.calculateResult()
    }

    fun part2(input: List<String>): Int {
        return BingoGame(input).findLoosingBoard()!!.calculateResult()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
