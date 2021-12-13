package day11

import com.google.common.truth.Truth
import print
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {

    class Octopus(matrix: Array<Array<Octopus>>, initValue: Int)

    fun parse(input: List<String>): Array<IntArray> {

        return input.map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    }

    fun getNeighbours(matrix: Array<IntArray>, position: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val result: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val (x, y) = position

        for (yN in max(y - 1, 0)..min(y + 1, matrix[0].lastIndex)) {
            for (xN in max(x - 1, 0)..min(x + 1, matrix[0].lastIndex)) {
                result.add(xN to yN)
            }
        }

        return result.minus(position)
    }

    fun modifyPointValues(matrix: Array<IntArray>, points: Collection<Pair<Int, Int>>, block: (Int) -> Int) {
        for (point in points) {
            val (x, y) = point
            matrix[y][x] = block(matrix[y][x])
        }
    }

    fun step(matrix: Array<IntArray>) {
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                matrix[y][x] = matrix[y][x] + 1
            }
        }

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] > 9) {
                    val neighbours = getNeighbours(matrix, x to y)
                    modifyPointValues(matrix, neighbours) { it + 1 }
                }
            }
        }

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] > 9) {
                    matrix[y][x] = 0
                }
            }
        }
    }

    fun countZeroes(matrix: Array<IntArray>): Int {
        var sum = 0
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] > 0) {
                    sum += 1
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {

        val matrix = parse(input)
        var flashes = 0

        matrix.print()

        for(i in 0..1) {
            step(matrix)
            matrix.print()
            flashes += countZeroes(matrix)
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day11_test")
    Truth.assertThat(part1(testInput)).isEqualTo(1656)
    Truth.assertThat(part2(testInput)).isEqualTo(0)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
