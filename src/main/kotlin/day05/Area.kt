package day05

import print

class Area(input: List<String>) {

    private val lines = input.map { Line(it) }

    fun buildEmpty(): Array<IntArray> {
        val maxX = lines.flatMap { it.getXs() }.maxOrNull() ?: throw IllegalStateException("Max X error")
        val maxY = lines.flatMap { it.getYs() }.maxOrNull() ?: throw IllegalStateException("Max Y error")

        return Array(maxX + 1) { IntArray(maxY + 1) }
    }

    fun fillWithVerticalAndHorizontal(matrix: Array<IntArray>) {
        lines
            .filter { it.x1 == it.x2 || it.y1 == it.y2 }
            .forEach {
                it.getPoints().forEach { (x, y) ->
                    matrix[x][y] = matrix[x][y] + 1
                }
            }
    }

    fun fillWithAllLines(matrix: Array<IntArray>) {
        lines
            .forEach {
                it.getPoints().forEach { (x, y) ->
                    matrix[x][y] = matrix[x][y] + 1
                }
            }
    }

    class Line(input: String) {
        val x1: Int
        val y1: Int
        val x2: Int
        val y2: Int

        init {
            val find = REGEXP.find(input) ?: throw IllegalStateException("Wrong input")
            val ints = find.groupValues.drop(1).map { it.toInt() }

            x1 = ints[0]
            y1 = ints[1]
            x2 = ints[2]
            y2 = ints[3]
        }

        fun getPoints(): List<Pair<Int, Int>> {

            val points = mutableListOf<Pair<Int, Int>>()

            when {
                x1 == x2 -> {
                    (y1 dto y2).forEach {
                        points.add(x1 to it)
                    }
                }
                y1 == y2 -> {
                    (x1 dto x2).forEach {
                        points.add(it to y1)
                    }
                }
                else -> {
                    val xsteps = x1 dto x2
                    val ysteps = y1 dto y2

                    xsteps.zip(ysteps).forEach {
                        points.add(it)
                    }
                }
            }

            return points
        }

        private infix fun Int.dto(other: Int): IntProgression {
            return if(this <= other) {
                (this..other)
            } else {
                (this downTo other)
            }
        }

        fun toList() = listOf(x1, y1, x2, y2)

        fun getXs() = listOf(x1, x2)

        fun getYs() = listOf(y1, y2)

        operator fun component1() = x1

        operator fun component2() = y1

        operator fun component3() = x2

        operator fun component4() = y2

        companion object {
            private val REGEXP = "(\\d+),(\\d+).+?(\\d+),(\\d+)".toRegex()
        }
    }
}