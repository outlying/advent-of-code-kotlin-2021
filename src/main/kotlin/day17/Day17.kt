package day17

import com.google.common.truth.Truth
import readInput

typealias Vector = Pair<Int, Int>
typealias Point = Pair<Int, Int>
typealias Area = Pair<IntRange, IntRange>

val Point.x: Int
    get() = first
val Point.y: Int
    get() = second
val Area.xRange
    get() = first
val Area.yRange
    get() = second

fun main() {
    fun part1(input: List<String>): Int {

        val prober = Prober(input[0])

        val vX = prober.calculateOptimalXVelocity()

        val yVal = prober.area.yRange.minOf { it } * (-1) - 1

        return (yVal downTo 0).fold(0) { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {

        val prober = Prober(input[0])

        // This is maximum x velocity that makes sense
        val maxXV = prober.area.xRange.last
        val minXV = prober.minXVelocityToReachArea()

        val maxYV = prober.area.yRange.minOf { it } * (-1) - 1
        val minYV = prober.area.yRange.minOf { it }

        var validVelocity = 0

        for (x in minXV..maxXV) {
            for (y in minYV..maxYV) {
                val simulation = prober.simulation(x to y)
                do {
                    simulation.step()

                    val isInTarget = simulation.isInTarget()
                    val isAreaPassed = simulation.passedArea()

                } while (!isInTarget && !isAreaPassed)

                val inTarget = simulation.isInTarget()

                if(inTarget) {
                    validVelocity = validVelocity + 1
                }
            }
        }

        return validVelocity
    }

    val testInput = readInput("Day17_test")
    Truth.assertThat(part1(testInput)).isEqualTo(45)
    Truth.assertThat(part2(testInput)).isEqualTo(112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
