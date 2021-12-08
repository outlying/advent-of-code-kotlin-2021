package day07

import readInput
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    fun parseInput(input: List<String>): List<Int> = input[0].split(',').map { it.toInt() }

    val simpleFuelCalculation: (input: List<Int>, position: Int) -> Int = { input, position ->
        input.sumOf { (it - position).absoluteValue }
    }

    val burnOutCalculations = mutableMapOf<Int,Int>()
    val burnoutFuelCalculation: (input: List<Int>, position: Int) -> Int = { input, position ->
        input.sumOf {
            val distance = (it - position).absoluteValue
            burnOutCalculations.getOrPut(distance) {
                (0..distance).sum()
            }
        }
    }

    fun calculateFuelPerPosition(
        input: List<Int>,
        fuelConsumtion: (input: List<Int>, position: Int) -> Int
    ): Map<Int, Int> {
        val minPosition = input.minOrNull()!!
        val maxPosition = input.maxOrNull()!!

        val testPositions = minPosition..maxPosition

        return testPositions.associateWith { fuelConsumtion(input, it) }
    }

    fun part1(input: List<String>): Int {
        val calculateFuelPerPosition = calculateFuelPerPosition(parseInput(input), simpleFuelCalculation)
        return calculateFuelPerPosition.values.minOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        val calculateFuelPerPosition = calculateFuelPerPosition(parseInput(input), burnoutFuelCalculation)
        return calculateFuelPerPosition.values.minOrNull() ?: 0
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    measureTime {
        println(part2(input))
    }.let { println(it) }
}
