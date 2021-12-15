package day11

import com.google.common.truth.Truth
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {

    class Octopus(private val position: Pair<Int, Int>, initValue: Int) {

        private var _value: Int = initValue
        val value: Int
            get() = _value

        private var flashed = false

        lateinit var matrix: Array<Array<Octopus>>

        fun increase(withFlash: Boolean) {
            _value += 1
            if (withFlash) {
                flash()
            } else {
                flashed = false
            }
        }

        fun flash() {
            if (_value > 9 && !flashed) {
                flashed = true
                getNeighbours().map { (x, y) -> matrix[y][x].increase(withFlash = true) }
            }
        }

        fun reset() {
            if (flashed) {
                _value = 0
                flashed = false
            }
        }

        private fun getNeighbours(): Set<Pair<Int, Int>> {
            val result: MutableSet<Pair<Int, Int>> = mutableSetOf()
            val (x, y) = position

            for (yN in max(y - 1, 0)..min(y + 1, matrix[0].lastIndex)) {
                for (xN in max(x - 1, 0)..min(x + 1, matrix[0].lastIndex)) {
                    result.add(xN to yN)
                }
            }

            return result.minus(position)
        }

        override fun toString(): String {
            return value.toString()
        }
    }

    fun parse(input: List<String>): Array<Array<Octopus>> {

        val matrix = input
            .mapIndexed { y, line ->
                line.mapIndexed { x, char -> Octopus(x to y, char.digitToInt()) }.toTypedArray()
            }.toTypedArray()

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                matrix[y][x].matrix = matrix
            }
        }

        return matrix
    }

    fun step(matrix: Array<Array<Octopus>>) {
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                matrix[y][x].increase(withFlash = false)
            }
        }

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                matrix[y][x].flash()
            }
        }

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                matrix[y][x].reset()
            }
        }
    }

    fun countZeroes(matrix: Array<Array<Octopus>>): Int {
        var sum = 0
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x].value == 0) {
                    sum += 1
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {

        val matrix = parse(input)
        var flashes = 0

        for (i in 1..100) {
            step(matrix)
            flashes += countZeroes(matrix)
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        val matrix = parse(input)
        var step = 0

        do {
            step += 1
            step(matrix)
        } while (countZeroes(matrix) != 100)

        return step
    }

    val testInput = readInput("Day11_test")
    Truth.assertThat(part1(testInput)).isEqualTo(1656)
    Truth.assertThat(part2(testInput)).isEqualTo(195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
