package day09

import com.google.common.truth.Truth
import readInput

fun main() {

    fun parseInput(input: List<String>): Array<IntArray> {
        return input
            .mapIndexed { y, row ->
                row.trim().toList()
                    .mapIndexed { x, char ->
                        try {
                            "$char".toInt()
                        } catch (e: Exception) {
                            throw IllegalStateException("Unable to convert '$char' at [$x, $y]")
                        }
                    }
                    .toIntArray()
            }
            .toTypedArray()
    }

    fun isLowPoint(matrix: Array<IntArray>, point: Pair<Int, Int>, surrounding: Set<Pair<Int, Int>>): Boolean {
        return surrounding.fold(point) { (accX, accY), (itemX, itemY) ->
            if (matrix[accY][accX] < matrix[itemY][itemX]) {
                accX to accY
            } else {
                itemX to itemY
            }
        } == point
    }

    fun getPointSurrounding(matrix: Array<IntArray>, point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val (x, y) = point

        var above: Pair<Int, Int>? = null
        var leftOf: Pair<Int, Int>? = null
        var rightOf: Pair<Int, Int>? = null
        var below: Pair<Int, Int>? = null

        if (y > 0) above = x to y - 1
        if (y < matrix.lastIndex) below = x to y + 1
        if (x > 0) leftOf = x - 1 to y
        if (x < matrix[0].lastIndex) rightOf = x + 1 to y

        return setOfNotNull(above, leftOf, rightOf, below)
    }

    fun findLowPoints(matrix: Array<IntArray>): List<Pair<Int, Int>> {

        val lowPoints: MutableList<Pair<Int, Int>> = mutableListOf()

        for (y in matrix.indices) {
            for (x in matrix[0].indices) {

                val point = x to y
                val pointsAround = getPointSurrounding(matrix, point)
                if (isLowPoint(matrix, point, pointsAround)) {
                    lowPoints.add(point)
                }
            }
        }

        return lowPoints
    }

    fun findBasins(matrix: Array<IntArray>, points: List<Pair<Int, Int>>): List<Set<Pair<Int, Int>>> {

        val basins: MutableList<Set<Pair<Int, Int>>> = mutableListOf()

        for (point in points) {
            val basin: MutableSet<Pair<Int, Int>> = mutableSetOf(point)

            do {
                val allSurroundings = basin
                    .flatMap { basinPoint -> getPointSurrounding(matrix, basinPoint) }
                    .toSet()
                val surroundingsWithout9 = allSurroundings
                    .filter { (x, y) -> matrix[y][x] != 9 }
                val newPoints = surroundingsWithout9
                    .minus(basin)

                if (newPoints.isNotEmpty()) {
                    basin.addAll(newPoints)
                }
            } while (newPoints.isNotEmpty())

            basins.add(basin)
        }

        return basins
    }

    fun part1(input: List<String>): Int {

        val matrix = parseInput(input)
        val lowPoints = findLowPoints(matrix)

        val riskValues = lowPoints.map { (x, y) -> matrix[y][x] + 1 }

        return riskValues.sum()
    }

    fun part2(input: List<String>): Int {

        val matrix = parseInput(input)
        val lowPoints = findLowPoints(matrix)
        val basins = findBasins(matrix, lowPoints)

        val biggest3 = basins.sortedBy { it.size }.asReversed().take(3).map { it.size }
        return biggest3.reduce { acc, i -> acc * i }
    }

    val testInput = readInput("Day09_test")
    Truth.assertThat(part1(testInput)).isEqualTo(15)
    Truth.assertThat(part2(testInput)).isEqualTo(1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
