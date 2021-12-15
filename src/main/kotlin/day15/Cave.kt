package day15

import java.util.*

typealias Point = Pair<Int, Int>

class Cave(input: List<String>, buildMegaMap: Boolean) {

    private val creationTime = System.currentTimeMillis()

    @Suppress("JoinDeclarationAndAssignment")
    private val rowsCount: Int
    private val colsCount: Int

    val map: Array<IntArray>
    private val startPoint: Point = 0 to 0
    private val endPoint: Point

    private val multiplier = if (buildMegaMap) 5 else 1

    init {
        rowsCount = input.size * multiplier
        colsCount = input[0].length * multiplier
        endPoint = colsCount - 1 to rowsCount - 1
        map = Array(rowsCount) { IntArray(colsCount) }

        input
            .forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    map[y][x] = char.toString().toInt()
                }
            }

        if (buildMegaMap) {
            val subRowsCount = input.size
            val subColsCount = input[0].length
            for (i in 0 until multiplier) {
                for (j in 0 until multiplier) {
                    val addedRisk = i + j

                    for (x in 0 until subColsCount) {
                        for (y in 0 until subRowsCount) {
                            var newValue = map[y][x] + addedRisk
                            if (newValue > 9) {
                                newValue -= 9
                            }
                            map[y + j * subRowsCount][x + i * subColsCount] = newValue
                        }
                    }
                }
            }
        }
    }

    fun calculateRisk(path: List<Point>): Int {
        return path.drop(1).map { (x, y) -> map[y][x] }.sum()
    }

    fun shortestPath(): LinkedList<Point> {
        return aStar(startPoint, endPoint)
    }

    private fun reconstructPath(cameMap: Map<Point, Point>, point: Point): LinkedList<Point> {
        var current = point
        val totalPath = LinkedList(listOf(current))
        while (cameMap.containsKey(current)) {
            current = cameMap[current]!!
            totalPath.add(0, current)
        }
        return totalPath
    }

    fun aStar(start: Point, goal: Point): LinkedList<Point> {

        val h: (Point) -> Int = { (x, y) ->
            val xDistance = goal.first - x
            val yDistance = goal.second - y
            xDistance + yDistance + 1
        }

        val openSet = mutableSetOf(start)
        val cameMap = mutableMapOf<Point, Point>()

        val gScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        gScore[start] = 0

        val fScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        fScore[start] = h(start)

        while (openSet.isNotEmpty()) {

            val current = openSet.minByOrNull { fScore.getValue(it) }
                ?: throw IllegalStateException("Minimum not found in openSet")

            if (current == goal) {
                return reconstructPath(cameMap, current)
            }

            openSet.remove(current)

            for (neighbor in current.neighbors()) {

                val d: (Point, Point) -> Int = { _, (x, y) -> map[y][x] }

                val gScoreCurrent = gScore.getValue(current)
                val gScoreNeighbor = gScore.getValue(neighbor)

                val tentativeGScore = gScoreCurrent + d(current, neighbor)

                if (tentativeGScore < gScoreNeighbor) {
                    cameMap[neighbor] = current
                    gScore[neighbor] = tentativeGScore
                    fScore[neighbor] = tentativeGScore + h(neighbor)
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor)
                    }
                }
            }
        }

        throw Error("Open set is empty but goal was never reached")
    }

    /**
     * All possible next points from given position
     */
    private fun Point.neighbors(): Set<Point> {
        val points = mutableSetOf<Point>()
        val (x, y) = this

        val isNotInLastColumn = x < endPoint.first
        val isNotInLastRow = y < endPoint.second

        if (isNotInLastColumn) {
            points.add(x + 1 to y)
        }

        if (isNotInLastRow) {
            points.add(x to y + 1)
        }

        if (x > 0) {
            points.add(x - 1 to y)
        }

        if (y > 0) {
            points.add(x to y - 1)
        }

        return points
    }
}