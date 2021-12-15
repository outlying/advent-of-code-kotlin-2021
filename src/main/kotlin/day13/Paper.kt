package day13

import print
import java.util.*

class Paper(input: List<String>) {


    private val folds: LinkedList<Pair<String, Int>>

    private var _area: Array<BooleanArray>
    val area: Array<BooleanArray>
        get() = _area

    init {

        val middle = input.indexOf(input.find { it.isBlank() })

        val points = input
            .take(middle)
            .map { line ->
                line.split(",").map { it.toInt() }
            }
            .map { it[0] to it[1] }

        folds = input
            .drop(middle + 1)
            .map { REGEXP_FOLD.find(it)!! }
            .map { it.groupValues }
            .map { it[1] to it[2].toInt() }
            .let { LinkedList(it) }

        val maxX = points.map { (x, _) -> x }.maxOf { it }
        val maxY = points.map { (_, y) -> y }.maxOf { it }

        _area = Array(maxY + 1) { BooleanArray(maxX + 1) { false } }

        points.onEach { (x, y) ->
            _area[y][x] = true
        }
    }

    fun fold() {

        val (hv, line) = folds.pop()

        var (first, second) = split(_area, hv, line)

        second = when (hv) {
            "y" -> second.flipVertically()
            "x" -> second.flipHorizontally()
            else -> throw IllegalStateException()
        }

        _area = first combine second
    }

    fun hasFoldsLeft(): Boolean {
        return folds.isNotEmpty()
    }

    companion object {

        private val REGEXP_FOLD = "fold along ([xy])=(\\d+)".toRegex()

        fun split(area: Array<BooleanArray>, hv: String, line: Int): Pair<Array<BooleanArray>, Array<BooleanArray>> {
            return when (hv) {
                "x" -> splitX(area, line)
                "y" -> splitY(area, line)
                else -> throw IllegalStateException()
            }
        }

        private fun splitX(area: Array<BooleanArray>, line: Int): Pair<Array<BooleanArray>, Array<BooleanArray>> {
            val rangeXpart1 = 0 until line
            val rangeXpart2 = (line + 1)..(area[0].lastIndex)
            val rangeY = area.indices

            val part1 = Array(rangeY.count()) { BooleanArray(rangeXpart1.count()) }
            val part2 = Array(rangeY.count()) { BooleanArray(rangeXpart2.count()) }

            for (x in rangeXpart1) {
                for (y in rangeY) {
                    part1[y][x] = area[y][x]
                }
            }

            for (x in rangeXpart2) {
                for (y in rangeY) {
                    part2[y][x - line - 1] = area[y][x]
                }
            }

            return part1 to part2

        }

        private fun splitY(area: Array<BooleanArray>, line: Int): Pair<Array<BooleanArray>, Array<BooleanArray>> {
            val rangeX = area[0].indices
            val rangeYpart1 = 0 until line
            val rangeYpart2 = (line + 1)..(area.lastIndex)

            val part1 = Array(rangeYpart1.count()) { BooleanArray(rangeX.count()) }
            val part2 = Array(rangeYpart2.count()) { BooleanArray(rangeX.count()) }

            for (x in rangeX) {
                for (y in rangeYpart1) {
                    part1[y][x] = area[y][x]
                }
            }

            for (x in rangeX) {
                for (y in rangeYpart2) {
                    part2[y - line - 1][x] = area[y][x]
                }
            }

            return part1 to part2
        }

        infix fun Array<BooleanArray>.combine(other: Array<BooleanArray>): Array<BooleanArray> {
            val ySize = this.size
            val xSize = this[0].size
            val combined = Array(ySize) { BooleanArray(xSize) { false } } // TODO not needed?

            for (y in this.indices) {
                for (x in this[0].indices) {
                    combined[y][x] = this[y][x] || other[y][x]
                }
            }

            return combined
        }

        fun Array<BooleanArray>.flipHorizontally(): Array<BooleanArray> {
            val ySize = this.size
            val xSize = this[0].size
            val newArray = Array(ySize) { BooleanArray(xSize) { false } }

            for (y in this.indices) {
                for (x in this[0].indices) {
                    newArray[y][xSize - 1 - x] = this[y][x]
                }
            }

            return newArray
        }

        fun Array<BooleanArray>.flipVertically(): Array<BooleanArray> {
            val ySize = this.size
            val xSize = this[0].size
            val newArray = Array(ySize) { BooleanArray(xSize) { false } }

            for (y in this.indices) {
                for (x in this[0].indices) {
                    newArray[ySize - 1 - y][x] = this[y][x]
                }
            }

            return newArray
        }
    }
}